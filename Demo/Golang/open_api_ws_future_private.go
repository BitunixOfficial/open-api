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

// OpenApiWsFuturePrivate represents the WebSocket client for private channels
type OpenApiWsFuturePrivate struct {
	config               *Config
	baseURL              string
	reconnectInterval    int
	messageQueue         chan map[string]interface{}
	conn                 *websocket.Conn
	isConnected          bool
	heartbeatInterval    int
	mu                   sync.Mutex
	maxReconnectAttempts int
	done                 chan struct{}
	firstConnected       chan struct{}
	closeOnce            sync.Once
}

// NewOpenApiWsFuturePrivate creates a new WebSocket client instance
func NewOpenApiWsFuturePrivate(config *Config) *OpenApiWsFuturePrivate {
	return &OpenApiWsFuturePrivate{
		config:               config,
		baseURL:              config.WebSocket.PrivateURI,
		reconnectInterval:    config.WebSocket.ReconnectInterval,
		messageQueue:         make(chan map[string]interface{}, 100),
		heartbeatInterval:    2, // Heartbeat interval in seconds
		maxReconnectAttempts: 1,
		done:                 make(chan struct{}),
		firstConnected:       make(chan struct{}),
	}
}

// Start starts the WebSocket client
func (c *OpenApiWsFuturePrivate) Start(ctx context.Context) error {
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

// Connect establishes WebSocket connection
func (c *OpenApiWsFuturePrivate) Connect(ctx context.Context) error {
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

			log.Println("WebSocket connection successful - private")

			// Authenticate the connection
			if err := c.authenticate(); err != nil {
				log.Printf("Authentication failed: %v", err)
				conn.Close()
				time.Sleep(time.Duration(c.reconnectInterval) * time.Second)
				reconnectAttempts++
				continue
			}

			// Start heartbeat
			pingCtx, cancelPing := context.WithCancel(ctx)
			go c.sendPing(pingCtx)

			// Start message consumption
			consumeCtx, cancelConsume := context.WithCancel(ctx)
			go c.consumeMessages(consumeCtx)

			// create a channel to notify connection disconnected
			disconnected := make(chan struct{})

			// Start message reading in a separate goroutine
			go func() {
				defer func() {
					cancelPing()
					cancelConsume()
					c.mu.Lock()
					c.isConnected = false
					c.mu.Unlock()
					close(disconnected) // notify connection disconnected
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
							// set connection status to disconnect, trigger reconnection
							c.mu.Lock()
							c.isConnected = false
							c.mu.Unlock()

							err := conn.Close()
							if err != nil {
								return
							}
							return
						}
						c.handleMessage(message)
					}
				}
			}()

			c.closeOnce.Do(func() {
				close(c.firstConnected)
			})

			// wait for connection disconnected
			select {
			case <-ctx.Done():
				return nil
			case <-c.done:
				return nil
			case <-disconnected:
				reconnectAttempts++
				log.Printf("Connection disconnected, attempting to reconnect... (attempt %d)", reconnectAttempts)
				time.Sleep(time.Duration(c.reconnectInterval) * time.Second)
				// reconnect
				continue
			}
		}
	}
}

// Stop stops the WebSocket client
func (c *OpenApiWsFuturePrivate) Stop() {
	c.mu.Lock()
	defer c.mu.Unlock()

	close(c.done) // close done channel
	if c.conn != nil {
		c.conn.Close()
	}
	c.isConnected = false
}

// sendPing sends heartbeat messages
func (c *OpenApiWsFuturePrivate) sendPing(ctx context.Context) {
	ticker := time.NewTicker(time.Duration(c.heartbeatInterval) * time.Second)
	defer ticker.Stop()

	for {
		select {
		case <-ctx.Done():
			return
		case <-c.done:
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

// authenticate authenticates the WebSocket connection
func (c *OpenApiWsFuturePrivate) authenticate() error {
	if !c.isConnected {
		return fmt.Errorf("WebSocket not firstConnected")
	}

	// generate signature
	nonce := generateNonce()
	timestamp := time.Now().Unix()
	signature := generateSign(nonce, fmt.Sprintf("%d", timestamp), c.config.Credentials.APIKey, c.config.Credentials.SecretKey)

	authMsg := map[string]interface{}{
		"op": "login",
		"args": []map[string]interface{}{{
			"apiKey":    c.config.Credentials.APIKey,
			"timestamp": timestamp,
			"nonce":     nonce,
			"sign":      signature,
		}},
	}

	log.Printf("Sending authentication message: %v", authMsg)
	c.mu.Lock()
	err := c.conn.WriteJSON(authMsg)
	c.mu.Unlock()
	if err != nil {
		return fmt.Errorf("authentication failed: %v", err)
	}

	log.Println("Authentication successful")
	return nil
}

// Subscribe subscribes to private channels
func (c *OpenApiWsFuturePrivate) Subscribe(channels []map[string]string) error {
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

	log.Println("Private channel subscription successful")
	return nil
}

// handleMessage processes received messages
func (c *OpenApiWsFuturePrivate) handleMessage(message []byte) {
	var data map[string]interface{}
	if err := json.Unmarshal(message, &data); err != nil {
		log.Printf("Failed to parse message: %v", err)
		return
	}

	log.Printf("Received raw message: %s", string(message))

	// Handle heartbeat response
	if op, ok := data["op"].(string); ok && op == "ping" {
		return
	}

	// check a message type
	if event, ok := data["event"].(string); ok {
		log.Printf("Received event: %s", event)
	}

	// check a channel
	if ch, ok := data["ch"].(string); ok {
		log.Printf("Received channel: %s", ch)
		// Define allowed private channels
		allowedChannels := map[string]bool{
			"balance":  true,
			"position": true,
			"order":    true,
			"tpsl":     true,
		}

		if allowedChannels[ch] {
			log.Printf("Putting message in queue for channel %s: %v", ch, data)
			c.messageQueue <- data
		} else {
			log.Printf("Channel %s not in allowed channels", ch)
		}
	} else {
		log.Printf("All channels are success: %v", data)
	}
}

// processMessage processes messages from the queue
func (c *OpenApiWsFuturePrivate) processMessage(message map[string]interface{}) {
	ch, ok := message["ch"].(string)
	if !ok {
		return
	}

	switch ch {
	case "balance":
		if balanceData, ok := message["data"].(map[string]interface{}); ok {
			log.Printf("\n=== Balance Update ===\n"+
				"Coin: %v\n"+
				"Available Balance: %v\n"+
				"Frozen Amount: %v\n"+
				"Isolation Frozen: %v\n"+
				"Cross Frozen: %v\n"+
				"Margin: %v\n"+
				"Isolation Margin: %v\n"+
				"Cross Margin: %v\n"+
				"Experience Money: %v\n"+
				"-------------------",
				balanceData["coin"],
				balanceData["available"],
				balanceData["frozen"],
				balanceData["isolationFrozen"],
				balanceData["crossFrozen"],
				balanceData["margin"],
				balanceData["isolationMargin"],
				balanceData["crossMargin"],
				balanceData["expMoney"])
		}

	case "position":
		if positionData, ok := message["data"].(map[string]interface{}); ok {
			log.Printf("\n=== Position Update ===\n"+
				"Event: %v\n"+
				"Position ID: %v\n"+
				"Margin Mode: %v\n"+
				"Position Mode: %v\n"+
				"Side: %v\n"+
				"Leverage: %v\n"+
				"Margin: %v\n"+
				"Create Time: %v\n"+
				"Quantity: %v\n"+
				"Entry Value: %v\n"+
				"Symbol: %v\n"+
				"Realized PnL: %v\n"+
				"Unrealized PnL: %v\n"+
				"Funding: %v\n"+
				"Fee: %v\n"+
				"-------------------",
				positionData["event"],
				positionData["positionId"],
				positionData["marginMode"],
				positionData["positionMode"],
				positionData["side"],
				positionData["leverage"],
				positionData["margin"],
				positionData["ctime"],
				positionData["qty"],
				positionData["entryValue"],
				positionData["symbol"],
				positionData["realizedPNL"],
				positionData["unrealizedPNL"],
				positionData["funding"],
				positionData["fee"])
		}

	case "order":
		if orderData, ok := message["data"].(map[string]interface{}); ok {
			log.Printf("\n=== Order Update ===\n"+
				"Order ID: %v\n"+
				"Symbol: %v\n"+
				"Type: %v\n"+
				"Status: %v\n"+
				"Price: %v\n"+
				"Quantity: %v\n"+
				"-------------------",
				orderData["orderId"],
				orderData["symbol"],
				orderData["type"],
				orderData["status"],
				orderData["price"],
				orderData["qty"])
		}

	case "tpsl":
		if tpslData, ok := message["data"].(map[string]interface{}); ok {
			log.Printf("\n=== TPSL Update ===\n"+
				"Symbol: %v\n"+
				"Order ID: %v\n"+
				"Position ID: %v\n"+
				"Leverage: %v\n"+
				"Side: %v\n"+
				"Position Mode: %v\n"+
				"Status: %v\n"+
				"Type: %v\n"+
				"SL Quantity: %v\n"+
				"TP Order Type: %v\n"+
				"SL Stop Type: %v\n"+
				"SL Price: %v\n"+
				"SL Order Price: %v\n"+
				"-------------------",
				tpslData["symbol"],
				tpslData["orderId"],
				tpslData["positionId"],
				tpslData["leverage"],
				tpslData["side"],
				tpslData["positionMode"],
				tpslData["status"],
				tpslData["type"],
				tpslData["slQty"],
				tpslData["tpOrderType"],
				tpslData["slStopType"],
				tpslData["slPrice"],
				tpslData["slOrderPrice"])
		}
	}
}

// consumeMessages processes messages from the queue
func (c *OpenApiWsFuturePrivate) consumeMessages(ctx context.Context) {
	for {
		select {
		case <-ctx.Done():
			return
		case message := <-c.messageQueue:
			c.processMessage(message)
		}
	}
}

func WsPrivateExampleUsage() {
	config, err := LoadConfig()
	if err != nil {
		log.Fatalf("Failed to load config: %v", err)
	}

	privateClient := NewOpenApiWsFuturePrivate(config)
	log.Printf("Starting private client...")
	if err := privateClient.Start(context.Background()); err != nil {
		log.Fatalf("Failed to start private client: %v", err)
	}

	//log.Printf("Subscribing to private channels...")
	//privateChannels := []map[string]string{
	//	//{"ch": "balance"},
	//	//{"ch": "position"},
	//	{"ch": "order"},
	//	//{"ch": "tpsl"},
	//}
	//if err := privateClient.Subscribe(privateChannels); err != nil {
	//	log.Fatalf("Failed to subscribe to private channels: %v", err)
	//}

	// create a channel to wait for interrupt signal
	waitDone := make(chan struct{})

	// start a goroutine to handle user input
	go func() {
		// you can add other exit conditions here
		// for example: read user input, or other control signals
		time.Sleep(60 * time.Second) // extend running time to 60s
		close(waitDone)
	}()

	log.Printf("Waiting for messages (press Ctrl+C to stop)...")
	<-waitDone // wait for exit signal

	log.Printf("Stopping private client...")
	privateClient.Stop()
}
