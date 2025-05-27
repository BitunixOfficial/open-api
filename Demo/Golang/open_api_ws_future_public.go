package main

import (
	"context"
	"encoding/json"
	"fmt"
	"log"
	"sync"
	"time"

	"github.com/gorilla/websocket"
)

// OpenApiWsFuturePublic represents the WebSocket client for public channels
type OpenApiWsFuturePublic struct {
	config               *Config
	baseURL              string
	reconnectInterval    int
	messageQueue         chan map[string]interface{}
	conn                 *websocket.Conn
	isConnected          bool
	heartbeatInterval    int
	maxReconnectAttempts int
	mu                   sync.Mutex
	done                 chan struct{} // add done channel
	firstConnected       chan struct{} // for sync connection status
	closeOnce            sync.Once
}

// NewOpenApiWsFuturePublic creates a new WebSocket client instance
func NewOpenApiWsFuturePublic(config *Config) *OpenApiWsFuturePublic {
	return &OpenApiWsFuturePublic{
		config:               config,
		baseURL:              config.WebSocket.PublicURI,
		reconnectInterval:    config.WebSocket.ReconnectInterval,
		messageQueue:         make(chan map[string]interface{}, 100),
		heartbeatInterval:    3, // Heartbeat interval in seconds
		maxReconnectAttempts: 1,
		done:                 make(chan struct{}),
		firstConnected:       make(chan struct{}), // initialize connected channel
	}
}

// Start starts the WebSocket client
func (c *OpenApiWsFuturePublic) Start(ctx context.Context) error {
	go func() {
		err := c.Connect(ctx)
		if err != nil {
			log.Printf("Failed to connect: %v", err)
			c.closeOnce.Do(func() {
				close(c.firstConnected)
			})
			return
		}
	}()

	// wait for connection success or timeout
	select {
	case <-c.firstConnected:
		if !c.isConnected {
			return fmt.Errorf("connection failed")
		}
		return nil
	case <-time.After(10 * time.Second):
		return fmt.Errorf("connection timeout")
	case <-ctx.Done():
		return ctx.Err()
	}
}

// Stop stops the WebSocket client
func (c *OpenApiWsFuturePublic) Stop() {
	c.mu.Lock()
	defer c.mu.Unlock()

	close(c.done)
	if c.conn != nil {
		c.conn.Close()
	}
	c.isConnected = false
}

// Connect establishes WebSocket connection
func (c *OpenApiWsFuturePublic) Connect(ctx context.Context) error {
	reconnectAttempts := 0

	for {
		select {
		case <-ctx.Done():
			return nil
		case <-c.done:
			return nil
		default:
			if reconnectAttempts >= c.maxReconnectAttempts {
				return fmt.Errorf("max reconnection attempts reached")
			}
			dialer := websocket.Dialer{}
			conn, _, err := dialer.Dial(c.baseURL, nil)
			if err != nil {
				log.Printf("WebSocket connection failed: %v", err)
				time.Sleep(time.Duration(c.reconnectInterval) * time.Second)
				reconnectAttempts++
				continue
			}

			c.mu.Lock()
			c.conn = conn
			c.isConnected = true
			c.mu.Unlock()

			log.Println("WebSocket connection successful - public")

			// Start heartbeat
			pingCtx, cancelPing := context.WithCancel(ctx)
			go c.sendPing(pingCtx)

			// Start message consumption
			consumeCtx, cancelConsume := context.WithCancel(ctx)
			go c.consumeMessages(consumeCtx)

			// Start message reading in a separate goroutine
			go func() {
				defer func() {
					cancelPing()
					cancelConsume()
					c.mu.Lock()
					c.isConnected = false
					c.mu.Unlock()
				}()

				for {
					select {
					case <-ctx.Done():
						return
					case <-c.done:
						return
					default:
						_, message, err := conn.ReadMessage()
						if err != nil {
							log.Printf("Error reading message: %v", err)
							return
						}
						c.handleMessage(message)
					}
				}
			}()

			c.closeOnce.Do(func() {
				close(c.firstConnected)
			})
			// Wait for context cancellation or done signal
			select {
			case <-ctx.Done():
				return nil
			case <-c.done:
				return nil
			}
		}
	}
}

// sendPing sends heartbeat messages
func (c *OpenApiWsFuturePublic) sendPing(ctx context.Context) {
	ticker := time.NewTicker(time.Duration(c.heartbeatInterval) * time.Second)
	defer ticker.Stop()

	for {
		select {
		case <-ctx.Done():
			return
		case <-ticker.C:
			if c.isConnected {
				pingMsg := map[string]interface{}{
					"op":   "ping",
					"ping": time.Now().Unix(),
				}
				c.mu.Lock()
				err := c.conn.WriteJSON(pingMsg)
				c.mu.Unlock()
				if err != nil {
					log.Printf("Failed to send ping: %v", err)
					c.isConnected = false
					return
				}
				log.Println("Sent ping message")
			}
		}
	}
}

// Subscribe subscribes to public channels
func (c *OpenApiWsFuturePublic) Subscribe(channels []map[string]string) error {
	if !c.isConnected {
		return fmt.Errorf("WebSocket not firstConnected")
	}

	subscribeMsg := map[string]interface{}{
		"op":   "subscribe",
		"args": channels,
	}

	log.Printf("Sending subscription message: %v", subscribeMsg)
	c.mu.Lock()
	err := c.conn.WriteJSON(subscribeMsg)
	c.mu.Unlock()
	if err != nil {
		return fmt.Errorf("subscription failed: %v", err)
	}

	log.Println("Public channel subscription successful")
	return nil
}

// handleMessage processes received messages
func (c *OpenApiWsFuturePublic) handleMessage(message []byte) {
	var data map[string]interface{}
	if err := json.Unmarshal(message, &data); err != nil {
		log.Printf("Failed to parse message: %v", err)
		return
	}

	log.Printf("Received raw message: %s", string(message))

	// Handle heartbeat response
	if op, ok := data["op"].(string); ok && op == "ping" {
		log.Printf("Received message: %v", data)
		return
	}

	// Define allowed public channels
	allowedChannels := map[string]bool{
		"depth_book1": true,
		"trade":       true,
		"ticker":      true,
	}

	if ch, ok := data["ch"].(string); ok && allowedChannels[ch] {
		log.Printf("Putting message in queue: %v", data)
		c.messageQueue <- data
	} else {
		log.Printf("Message not for allowed channels: %v", data)
	}
}

// processMessage processes messages from the queue
func (c *OpenApiWsFuturePublic) processMessage(message map[string]interface{}) {
	ch, ok := message["ch"].(string)
	if !ok {
		log.Printf("Message missing channel: %v", message)
		return
	}

	log.Printf("Processing message for channel %s: %v", ch, message)

	switch ch {
	case "trade":
		// handle real-time trade data
		switch tradeData := message["data"].(type) {
		case []interface{}:
			// handle array format trade data
			for _, trade := range tradeData {
				if tradeMap, ok := trade.(map[string]interface{}); ok {
					log.Printf("Trade: Time=%v, Price=%v, Volume=%v, Side=%v",
						tradeMap["t"],
						tradeMap["p"],
						tradeMap["v"],
						tradeMap["s"])
				}
			}
		default:
			log.Printf("Invalid trade data format: %v", message)
		}
	case "ticker":
		// handle 24-hour market data
		if tickerData, ok := message["data"].(map[string]interface{}); ok {
			log.Printf("Received 24h ticker: %v", tickerData)
		}
	case "depth_book1":
		// handle order book depth data
		if depthData, ok := message["data"].(map[string]interface{}); ok {
			log.Printf("Received order book depth: %v", depthData)
		}
	}
}

// consumeMessages processes messages from the queue
func (c *OpenApiWsFuturePublic) consumeMessages(ctx context.Context) {
	for {
		select {
		case <-ctx.Done():
			return
		case message := <-c.messageQueue:
			c.processMessage(message)
		}
	}
}

func WsPublicExampleUsage() {
	config, err := LoadConfig()
	if err != nil {
		log.Fatalf("Failed to load config: %v", err)
	}

	publicClient := NewOpenApiWsFuturePublic(config)
	log.Printf("Starting public client...")
	if err := publicClient.Start(context.Background()); err != nil {
		log.Fatalf("Failed to start public client: %v", err)
	}

	log.Printf("Subscribing to public channels...")
	publicChannels := []map[string]string{
		{"symbol": "BTCUSDT", "ch": "trade"},
		{"symbol": "BTCUSDT", "ch": "ticker"},
		{"symbol": "BTCUSDT", "ch": "depth_book1"},
	}
	if err := publicClient.Subscribe(publicChannels); err != nil {
		log.Fatalf("Failed to subscribe to public channels: %v", err)
	}

	// create a channel to wait for interrupt signal
	waitDone := make(chan struct{})

	// start a goroutine to handle user input
	go func() {
		// you can add other exit conditions here
		// for example: read user input, or other control signals
		time.Sleep(5 * time.Second) // extend running time to 5s
		close(waitDone)
	}()

	log.Printf("Waiting for messages (press Ctrl+C to stop)...")
	<-waitDone // wait for exit signal

	log.Printf("Stopping public client...")
	publicClient.Stop()
}
