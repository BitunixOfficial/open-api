package main

import (
	"bytes"
	"encoding/json"
	"fmt"
	"io"
	"net/http"
	"strings"
)

// OpenApiHttpFuturePrivate future private api client
type OpenApiHttpFuturePrivate struct {
	apiKey    string
	secretKey string
	baseURL   string
	client    *http.Client
}

// NewOpenApiHttpFuturePrivate create new future private api client
func NewOpenApiHttpFuturePrivate(config *Config) *OpenApiHttpFuturePrivate {
	return &OpenApiHttpFuturePrivate{
		apiKey:    config.Credentials.APIKey,
		secretKey: config.Credentials.SecretKey,
		baseURL:   config.HTTP.URIPrefix,
		client:    &http.Client{},
	}
}

// handleResponse handle future private api response
func (c *OpenApiHttpFuturePrivate) handleResponse(resp *http.Response) (map[string]interface{}, error) {
	if resp.StatusCode != http.StatusOK {
		return nil, fmt.Errorf("HTTP Error: %d", resp.StatusCode)
	}

	body, err := io.ReadAll(resp.Body)
	if err != nil {
		return nil, err
	}

	var result map[string]interface{}
	if err := json.Unmarshal(body, &result); err != nil {
		return nil, err
	}

	if code, ok := result["code"].(float64); ok && code != 0 {
		msg, _ := result["msg"].(string)
		return nil, fmt.Errorf("API Error: %v - %s", code, msg)
	}

	if data, ok := result["data"].(map[string]interface{}); ok {
		return data, nil
	}

	return nil, fmt.Errorf("invalid response format")
}

// BuildQueryString build get request query string
func BuildQueryStringPrivate(params map[string]string) string {
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

// GetAccount get account info
func (c *OpenApiHttpFuturePrivate) GetAccount(marginCoin string) (map[string]interface{}, error) {
	if marginCoin == "" {
		marginCoin = "USDT"
	}
	params := make(map[string]string)
	params["marginCoin"] = marginCoin

	headers := getAuthHeadersPrivate(c.apiKey, c.secretKey, params, "")

	// build full url
	queryStr := BuildQueryStringPrivate(params)
	req, err := http.NewRequest("GET", c.baseURL+"/api/v1/futures/account"+queryStr, nil)
	if err != nil {
		return nil, err
	}

	for k, v := range headers {
		req.Header.Set(k, v)
	}

	resp, err := c.client.Do(req)
	if err != nil {
		return nil, err
	}
	defer resp.Body.Close()

	return c.handleResponse(resp)
}

// PlaceOrder place order
func (c *OpenApiHttpFuturePrivate) PlaceOrder(params map[string]interface{}) (map[string]interface{}, error) {
	jsonData, err := json.Marshal(params)
	if err != nil {
		return nil, err
	}

	headers := getAuthHeadersPrivate(c.apiKey, c.secretKey, map[string]string{}, string(jsonData))

	req, err := http.NewRequest("POST", c.baseURL+"/api/v1/futures/trade/place_order", bytes.NewBuffer(jsonData))
	if err != nil {
		return nil, err
	}

	for k, v := range headers {
		req.Header.Set(k, v)
	}

	resp, err := c.client.Do(req)
	if err != nil {
		return nil, err
	}
	defer resp.Body.Close()

	return c.handleResponse(resp)
}

// CancelOrders cancel orders
func (c *OpenApiHttpFuturePrivate) CancelOrders(symbol string, orderList []map[string]string) (map[string]interface{}, error) {
	data := map[string]interface{}{
		"symbol":    symbol,
		"orderList": orderList,
	}

	jsonData, err := json.Marshal(data)
	if err != nil {
		return nil, err
	}

	headers := getAuthHeadersPrivate(c.apiKey, c.secretKey, map[string]string{}, string(jsonData))

	req, err := http.NewRequest("POST", c.baseURL+"/api/v1/futures/trade/cancel_orders", bytes.NewBuffer(jsonData))
	if err != nil {
		return nil, err
	}

	for k, v := range headers {
		req.Header.Set(k, v)
	}

	resp, err := c.client.Do(req)
	if err != nil {
		return nil, err
	}
	defer resp.Body.Close()

	return c.handleResponse(resp)
}

// GetHistoryOrders get history orders
func (c *OpenApiHttpFuturePrivate) GetHistoryOrders(symbol string) (map[string]interface{}, error) {
	params := make(map[string]string)
	if symbol != "" {
		params["symbol"] = symbol
	}

	headers := getAuthHeadersPrivate(c.apiKey, c.secretKey, params, "")

	// build url query params
	queryStr := BuildQueryStringPrivate(params)
	req, err := http.NewRequest("GET", c.baseURL+"/api/v1/futures/trade/get_history_orders"+queryStr, nil)
	if err != nil {
		return nil, err
	}

	for k, v := range headers {
		req.Header.Set(k, v)
	}

	resp, err := c.client.Do(req)
	if err != nil {
		return nil, err
	}
	defer resp.Body.Close()

	return c.handleResponse(resp)
}

// GetHistoryPositions get history positions
func (c *OpenApiHttpFuturePrivate) GetHistoryPositions(symbol string) (map[string]interface{}, error) {
	params := make(map[string]string)
	if symbol != "" {
		params["symbol"] = symbol
	}

	headers := getAuthHeadersPrivate(c.apiKey, c.secretKey, params, "")

	// build url query params
	queryStr := BuildQueryStringPrivate(params)
	req, err := http.NewRequest("GET", c.baseURL+"/api/v1/futures/position/get_history_positions"+queryStr, nil)
	if err != nil {
		return nil, err
	}

	for k, v := range headers {
		req.Header.Set(k, v)
	}

	resp, err := c.client.Do(req)
	if err != nil {
		return nil, err
	}
	defer resp.Body.Close()

	return c.handleResponse(resp)
}

// getAuthHeadersPrivate get auth headers
func getAuthHeadersPrivate(apiKey string, secretKey string, queryParam map[string]string, body string) map[string]string {
	queryString := SortParams(queryParam)
	headers := GetAuthHeaders(apiKey, secretKey, queryString, body)
	headers["language"] = "en-US"
	headers["Content-Type"] = "application/json"
	return headers
}

// HttpPrivateExampleUsage main function
func HttpPrivateExampleUsage() {
	// load config
	config, err := LoadConfig()
	if err != nil {
		fmt.Printf("load config failed: %v\n", err)
		return
	}

	// create client
	client := NewOpenApiHttpFuturePrivate(config)

	// get account info
	account, err := client.GetAccount("USDT")
	if err != nil {
		fmt.Printf("get account info failed: %v\n", err)
		return
	}
	fmt.Printf("account info: %+v\n", account)

	// get history positions
	positions, err := client.GetHistoryPositions("BTCUSDT")
	if err != nil {
		fmt.Printf("get history positions failed: %v\n", err)
		return
	}
	fmt.Printf("history positions: %+v\n", positions)

	// get history orders
	orders, err := client.GetHistoryOrders("BTCUSDT")
	if err != nil {
		fmt.Printf("get history orders failed: %v\n", err)
		return
	}
	fmt.Printf("history orders: %+v\n", orders)

	/*
		WARNING!!! This is example code for placing and canceling orders. If you are using a real account,
		please be cautious when uncommenting for testing, as any financial losses will be your responsibility.
	*/
	// // Example order placement (limit order)
	// order, err := client.PlaceOrder(map[string]interface{}{
	// 	"symbol":       "BTCUSDT",
	// 	"side":         "BUY",
	// 	"orderType":    "LIMIT",
	// 	"qty":          "0.5",
	// 	"price":        "60000",
	// 	"tradeSide":    "OPEN",
	// 	"effect":       "GTC",
	// 	"reduceOnly":   false,
	// 	"clientId":     time.Now().Format("20060102150405"),
	// 	"tpPrice":      "61000",
	// 	"tpStopType":   "MARK",
	// 	"tpOrderType":  "LIMIT",
	// 	"tpOrderPrice": "61000.1",
	// })
	// if err != nil {
	// 	fmt.Printf("place order failed: %v\n", err)
	// 	return
	// }
	// fmt.Printf("place order result: %+v\n", order)

	// // Example order cancellation
	// if order != nil && order["orderId"] != nil {
	// 	cancel_result, err := client.CancelOrders("BTCUSDT", []map[string]string{
	// 		{"orderId": order["orderId"].(string)},
	// 		{"clientId": order["clientId"].(string)},
	// 	})
	// 	if err != nil {
	// 		fmt.Printf("cancel order failed: %v\n", err)
	// 		return
	// 	}
	// 	fmt.Printf("cancel order result: %+v\n", cancel_result)
	// }

}
