package main

import (
	"crypto/sha256"
	"encoding/hex"
	"fmt"
	"sort"
	"strings"
	"time"

	"github.com/google/uuid"
)

// GetNonce generates a random string as nonce
func GetNonce() string {
	// generate UUID and remove hyphens
	return strings.ReplaceAll(uuid.New().String(), "-", "")
}

// GetTimestamp gets current timestamp in milliseconds
func GetTimestamp() string {
	return fmt.Sprintf("%d", time.Now().UnixMilli())
}

// GenerateSignature generates signature according to Bitunix OpenAPI doc
func GenerateSignature(apiKey, secretKey, nonce, timestamp, queryParams, body string) string {
	digestInput := strings.TrimSpace(nonce + timestamp + apiKey + queryParams + body)
	digest := sha256.Sum256([]byte(digestInput))
	digestStr := hex.EncodeToString(digest[:])

	signInput := strings.TrimSpace(digestStr + secretKey)
	sign := sha256.Sum256([]byte(signInput))
	return hex.EncodeToString(sign[:])
}

// GetAuthHeaders gets authentication headers
func GetAuthHeaders(apiKey, secretKey, queryParams, body string) map[string]string {
	nonce := GetNonce()
	timestamp := GetTimestamp()

	sign := GenerateSignature(
		apiKey,
		secretKey,
		nonce,
		timestamp,
		queryParams,
		body,
	)

	return map[string]string{
		"api-key":   apiKey,
		"sign":      sign,
		"nonce":     nonce,
		"timestamp": timestamp,
	}
}

// SortParams sorts parameters and concatenates them
func SortParams(params map[string]string) string {
	if len(params) == 0 {
		return ""
	}

	// get all keys and sort them
	keys := make([]string, 0, len(params))
	for k := range params {
		keys = append(keys, k)
	}
	sort.Strings(keys)

	// concatenate parameters by sorted keys
	var builder strings.Builder
	for _, k := range keys {
		builder.WriteString(k)
		builder.WriteString(params[k])
	}
	return builder.String()
}
