package main

import (
	"crypto/sha256"
	"encoding/hex"
	"fmt"
	"math/rand"
	"time"
)

// generateNonce generates a random string as nonce
func generateNonce() string {
	const charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
	const length = 32
	b := make([]byte, length)
	for i := range b {
		b[i] = charset[rand.Intn(len(charset))]
	}
	return string(b)
}

// generateTimestamp generates the current timestamp
func generateTimestamp() string {
	return fmt.Sprintf("%d", time.Now().Unix())
}

// sha256Hex calculates the SHA256 hash of the input string
func sha256Hex(input string) string {
	hash := sha256.Sum256([]byte(input))
	return hex.EncodeToString(hash[:])
}

// generateSign generates authentication signature
func generateSign(nonce, timestamp, apiKey, secretKey string) string {
	digestInput := nonce + timestamp + apiKey
	digest := sha256Hex(digestInput)
	signInput := digest + secretKey
	return sha256Hex(signInput)
}

// WSAuth defines the WebSocket authentication data structure
type WSAuth struct {
	APIKey    string `json:"apiKey"`
	Timestamp int64  `json:"timestamp"`
	Nonce     string `json:"nonce"`
	Sign      string `json:"sign"`
}

// GetAuthWSFuture generates WebSocket authentication data
func GetAuthWSFuture(apiKey, secretKey string) WSAuth {
	nonce := generateNonce()
	timestamp := generateTimestamp()
	sign := generateSign(nonce, timestamp, apiKey, secretKey)

	// Convert timestamp string to int64
	timestampInt, _ := time.Parse(time.RFC3339, timestamp)

	return WSAuth{
		APIKey:    apiKey,
		Timestamp: timestampInt.Unix(),
		Nonce:     nonce,
		Sign:      sign,
	}
}
