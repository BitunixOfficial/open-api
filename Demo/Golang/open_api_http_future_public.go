package main

import (
	"encoding/json"
	"fmt"
	"io"
	"net/http"
	"strings"
	"time"
)

// OpenApiHttpFuturePublic future public api client
type OpenApiHttpFuturePublic struct {
	baseURL string
	client  *http.Client
	config  *Config
}

// NewOpenApiHttpFuturePublic create a new future public api client
func NewOpenApiHttpFuturePublic(config *Config) *OpenApiHttpFuturePublic {
	return &OpenApiHttpFuturePublic{
		baseURL: config.HTTP.URIPrefix,
		client:  &http.Client{},
		config:  config,
	}
}

// handleResponse handle api response
func (c *OpenApiHttpFuturePublic) handleResponse(resp *http.Response) (interface{}, error) {
	if resp.StatusCode != http.StatusOK {
		return nil, fmt.Errorf("HTTP Error: %d", resp.StatusCode)
	}

	body, err := io.ReadAll(resp.Body)
	if err != nil {
		return nil, err
	}

	var result struct {
		Code int         `json:"code"`
		Msg  string      `json:"msg"`
		Data interface{} `json:"data"`
	}

	if err := json.Unmarshal(body, &result); err != nil {
		return nil, err
	}

	if result.Code != 0 {
		error := GetByCode(result.Code)
		if error != nil {
			return nil, fmt.Errorf("%s", error)
		}
		return nil, fmt.Errorf("unknown error: %d - %s", result.Code, result.Msg)
	}

	return result.Data, nil
}

// BuildQueryString build get request query string
func BuildQueryString(params map[string]string) string {
	if len(params) == 0 {
		return ""
	}

	var queryString strings.Builder
	first := true
	for k, v := range params {
		if !first {
			queryString.WriteString("&")
		}
		queryString.WriteString(k)
		queryString.WriteString("=")
		queryString.WriteString(v)
		first = false
	}
	return "?" + queryString.String()
}

// GetTickers get future trading pair market data
// Rate Limit: 10 req/sec/ip
func (c *OpenApiHttpFuturePublic) GetTickers(symbols string) ([]interface{}, error) {
	params := make(map[string]string)
	if symbols != "" {
		params["symbols"] = symbols
	}

	queryString := BuildQueryString(params)
	req, err := http.NewRequest("GET", c.baseURL+"/api/v1/futures/market/tickers"+queryString, nil)
	if err != nil {
		return nil, err
	}

	req.Header.Set("Content-Type", "application/json")

	resp, err := c.client.Do(req)
	if err != nil {
		return nil, err
	}
	defer resp.Body.Close()

	data, err := c.handleResponse(resp)
	if err != nil {
		return nil, err
	}

	result, ok := data.([]interface{})
	if !ok {
		return nil, fmt.Errorf("unexpected response type: %T", data)
	}

	return result, nil
}

// GetDepth get depth data
// Rate Limit: 10 req/sec/ip
// limit: Fixed gear enumeration value: 1/5/15/50/max
func (c *OpenApiHttpFuturePublic) GetDepth(symbol string, limit string) (map[string]interface{}, error) {
	params := make(map[string]string)
	params["symbol"] = symbol
	params["limit"] = limit

	queryString := BuildQueryString(params)
	req, err := http.NewRequest("GET", c.baseURL+"/api/v1/futures/market/depth"+queryString, nil)
	if err != nil {
		return nil, err
	}

	req.Header.Set("Content-Type", "application/json")

	resp, err := c.client.Do(req)
	if err != nil {
		return nil, err
	}
	defer resp.Body.Close()

	data, err := c.handleResponse(resp)
	if err != nil {
		return nil, err
	}

	result, ok := data.(map[string]interface{})
	if !ok {
		return nil, fmt.Errorf("unexpected response type: %T", data)
	}

	return result, nil
}

// GetKline get kline data
// Rate Limit: 10 req/sec/ip
// interval: kline interval such as 1m 5m 15m 30m 1h 2h 4h 6h 8h 12h 1d 3d 1w 1M
// type: Kline type, values: LAST_PRICE, MARK_PRICE; default: LAST_PRICE
func (c *OpenApiHttpFuturePublic) GetKline(symbol, interval string, limit int, startTime, endTime *int64, klineType string) ([]interface{}, error) {
	params := make(map[string]string)
	params["symbol"] = symbol
	params["interval"] = interval
	params["limit"] = fmt.Sprintf("%d", limit)

	if klineType == "" {
		klineType = "LAST_PRICE"
	}
	params["type"] = klineType

	if startTime != nil {
		params["startTime"] = fmt.Sprintf("%d", *startTime)
	}
	if endTime != nil {
		params["endTime"] = fmt.Sprintf("%d", *endTime)
	}

	queryString := BuildQueryString(params)
	req, err := http.NewRequest("GET", c.baseURL+"/api/v1/futures/market/kline"+queryString, nil)
	if err != nil {
		return nil, err
	}

	req.Header.Set("Content-Type", "application/json")

	resp, err := c.client.Do(req)
	if err != nil {
		return nil, err
	}
	defer resp.Body.Close()

	data, err := c.handleResponse(resp)
	if err != nil {
		return nil, err
	}

	result, ok := data.([]interface{})
	if !ok {
		return nil, fmt.Errorf("unexpected response type: %T", data)
	}

	return result, nil
}

// GetFundingRate get funding rate
// Rate Limit: 10 req/sec/ip
func (c *OpenApiHttpFuturePublic) GetFundingRate(symbol string) (map[string]interface{}, error) {
	params := make(map[string]string)
	params["symbol"] = symbol

	queryString := BuildQueryString(params)
	req, err := http.NewRequest("GET", c.baseURL+"/api/v1/futures/market/funding_rate"+queryString, nil)
	if err != nil {
		return nil, err
	}

	req.Header.Set("Content-Type", "application/json")

	resp, err := c.client.Do(req)
	if err != nil {
		return nil, err
	}
	defer resp.Body.Close()

	data, err := c.handleResponse(resp)
	if err != nil {
		return nil, err
	}

	result, ok := data.(map[string]interface{})
	if !ok {
		return nil, fmt.Errorf("unexpected response type: %T", data)
	}

	return result, nil
}

// GetTradingPairs get trading pair info
// Rate Limit: 10 req/sec/ip
func (c *OpenApiHttpFuturePublic) GetTradingPairs() ([]interface{}, error) {
	req, err := http.NewRequest("GET", c.baseURL+"/api/v1/futures/market/trading_pairs", nil)
	if err != nil {
		return nil, err
	}

	req.Header.Set("Content-Type", "application/json")

	resp, err := c.client.Do(req)
	if err != nil {
		return nil, err
	}
	defer resp.Body.Close()

	data, err := c.handleResponse(resp)
	if err != nil {
		return nil, err
	}

	result, ok := data.([]interface{})
	if !ok {
		return nil, fmt.Errorf("unexpected response type: %T", data)
	}

	return result, nil
}

// HttpPublicExampleUsage example usage
func HttpPublicExampleUsage() {
	// load config
	config, err := LoadConfig()
	if err != nil {
		fmt.Printf("load config failed: %v\n", err)
		return
	}

	// create client
	client := NewOpenApiHttpFuturePublic(config)

	// get market data
	tickers, err := client.GetTickers("BTCUSDT,ETHUSDT")
	if err != nil {
		fmt.Printf("get market data failed: %v\n", err)
		return
	}
	fmt.Printf("market data: %+v\n", tickers)

	// get depth data
	depth, err := client.GetDepth("BTCUSDT", "5")
	if err != nil {
		fmt.Printf("get depth data failed: %v\n", err)
		return
	}
	fmt.Printf("depth data: %+v\n", depth)

	// get kline data
	currentTime := time.Now().UnixMilli()
	oneHourAgo := currentTime - (60 * 60 * 1000)
	klines, err := client.GetKline("BTCUSDT", "1m", 5, &oneHourAgo, &currentTime, "")
	if err != nil {
		fmt.Printf("get kline data failed: %v\n", err)
		return
	}
	fmt.Printf("kline data: %+v\n", klines)

	// get funding rate
	fundingRate, err := client.GetFundingRate("BTCUSDT")
	if err != nil {
		fmt.Printf("get funding rate failed: %v\n", err)
		return
	}
	fmt.Printf("funding rate: %+v\n", fundingRate)

	// get trading pair info
	tradingPairs, err := client.GetTradingPairs()
	if err != nil {
		fmt.Printf("get trading pair info failed: %v\n", err)
		return
	}
	fmt.Printf("trading pair info: %+v\n", tradingPairs)
}
