<?php

require_once 'config.php';
require_once 'error_codes.php';
require_once 'open_api_http_sign.php';

/**
 * OpenAPI http future private api client
 */
class OpenApiHttpFuturePrivate {
    private Config $config;
    private string $apiKey;
    private string $secretKey;
    private string $baseUrl;
    private array $defaultHeaders;

    /**
     * initialize OpenApiHttpFuturePrivate class
     * 
     * @param Config $config config object
     */
    public function __construct(Config $config) {
        $this->config = $config;
        $this->apiKey = $config->getApiKey();
        $this->secretKey = $config->getSecretKey();
        $this->baseUrl = $config->getUriPrefix();
        
        // set common request headers
        $this->defaultHeaders = [
            "language" => "en-US",
            "Content-Type" => "application/json"
        ];
    }

    /**
     * handle response
     * 
     * @param array $response response data
     * @return array processed data
     * @throws Exception when the response status code is not 200 or the business status code is not 0
     */
    private function handleResponse(array $response): array {
        if ($response['http_code'] !== 200) {
            throw new Exception("HTTP Error: " . $response['http_code']);
        }

        $data = json_decode($response['body'], true);
        if ($data['code'] !== 0) {
            $error = ErrorCode::getByCode($data['code']);
            if ($error) {
                throw new Exception(json_encode($error));
            }
            throw new Exception("Unknown Error: {$data['code']} - {$data['msg']}");
        }

        return $data['data'];
    }

    /**
    * send http request
     * 
     * @param string $method request method
     * @param string $url request url
     * @param array $params query params
     * @param array $data request body data
     * @return array response data
     */
    private function sendRequest(string $method, string $url, array $params = [], array $data = []): array {
        $ch = curl_init();
        
        // build full url 
        if (!empty($params)) {
            $url .= '?' . http_build_query($params);
        }
        
        // set curl options
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        
        // prepare request headers
        $headers = $this->defaultHeaders;
        
        if ($method === 'POST') {
            curl_setopt($ch, CURLOPT_POST, true);
            if (!empty($data)) {
                $jsonData = json_encode($data);
                curl_setopt($ch, CURLOPT_POSTFIELDS, $jsonData);
                $authHeaders = OpenApiHttpSign::getAuthHeaders($this->apiKey, $this->secretKey, [], $jsonData);
            } else {
                $authHeaders = OpenApiHttpSign::getAuthHeaders($this->apiKey, $this->secretKey);
            }
        } else {
            $authHeaders = OpenApiHttpSign::getAuthHeaders($this->apiKey, $this->secretKey, $params);
        }
        
        // merge all headers
        $headers = array_merge($headers, $authHeaders);
        
        // set request headers
        $headerArray = [];
        foreach ($headers as $key => $value) {
            $headerArray[] = "$key: $value";
        }
        curl_setopt($ch, CURLOPT_HTTPHEADER, $headerArray);
        
        // execute request
        $response = curl_exec($ch);
        $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
        curl_close($ch);
        
        return [
            'http_code' => $httpCode,
            'body' => $response
        ];
    }

    /**
     * get account info
     * 
     * @param string $marginCoin margin coin, default USDT
     * @return array account info
     */
    public function getAccount(string $marginCoin = "USDT"): array {
        $url = $this->baseUrl . "/api/v1/futures/account";
        $params = [
            "marginCoin" => $marginCoin
        ];
        
        $response = $this->sendRequest('GET', $url, $params);
        return $this->handleResponse($response);
    }

    /**
     * place order
     * 
     * @param string $symbol symbol
     * @param string $side side, BUY or SELL
     * @param string $orderType order type, LIMIT or MARKET
     * @param string $qty quantity
     * @param string|null $price price, required when orderType is LIMIT
     * @param string|null $positionId position id
     * @param string $tradeSide trade side, OPEN (open position) or CLOSE (close position), default OPEN
     * @param string $effect order effective time, default GTC
     * @param bool $reduceOnly reduce only
     * @param string|null $clientId client order id
     * @param string|null $tpPrice take profit price
     * @param string|null $tpStopType take profit stop type, MARK (mark price) or LAST (last price)
     * @param string|null $tpOrderType take profit order type, LIMIT or MARKET
     * @param string|null $tpOrderPrice take profit order price
     * @return array order info
     */
    public function placeOrder(
        string $symbol,
        string $side,
        string $orderType,
        string $qty,
        ?string $price = null,
        ?string $positionId = null,
        string $tradeSide = "OPEN",
        string $effect = "GTC",
        bool $reduceOnly = false,
        ?string $clientId = null,
        ?string $tpPrice = null,
        ?string $tpStopType = null,
        ?string $tpOrderType = null,
        ?string $tpOrderPrice = null
    ): array {
        $url = $this->baseUrl . "/api/v1/futures/trade/place_order";
        
        $data = [
            "symbol" => $symbol,
            "side" => $side,
            "orderType" => $orderType,
            "qty" => $qty,
            "tradeSide" => $tradeSide,
            "effect" => $effect,
            "reduceOnly" => $reduceOnly
        ];
        
        if ($price !== null) {
            $data["price"] = $price;
        }
        if ($positionId !== null) {
            $data["positionId"] = $positionId;
        }
        if ($clientId !== null) {
            $data["clientId"] = $clientId;
        }
        if ($tpPrice !== null) {
            $data["tpPrice"] = $tpPrice;
        }
        if ($tpStopType !== null) {
            $data["tpStopType"] = $tpStopType;
        }
        if ($tpOrderType !== null) {
            $data["tpOrderType"] = $tpOrderType;
        }
        if ($tpOrderPrice !== null) {
            $data["tpOrderPrice"] = $tpOrderPrice;
        }
        
        $response = $this->sendRequest('POST', $url, [], $data);
        return $this->handleResponse($response);
    }

    /**
     * cancel orders
     * 
     * @param string $symbol symbol
     * @param array $orderList order list, each order contains orderId or clientId
     * @return array cancel order result
     */
    public function cancelOrders(string $symbol, array $orderList): array {
        $url = $this->baseUrl . "/api/v1/futures/trade/cancel_orders";
        
        $data = [
            "symbol" => $symbol,
            "orderList" => $orderList
        ];
        
        $response = $this->sendRequest('POST', $url, [], $data);
        return $this->handleResponse($response);
    }

    /**
     * get history orders
     * 
     * @param string|null $symbol symbol, null for all symbols
     * @return array history orders list
     */
    public function getHistoryOrders(?string $symbol = null): array {
        $url = $this->baseUrl . "/api/v1/futures/trade/get_history_orders";
        $params = [];
        if ($symbol) {
            $params["symbol"] = $symbol;
        }
        
        $response = $this->sendRequest('GET', $url, $params);
        return $this->handleResponse($response);
    }

    /**
     * get history positions
     * 
     * @param string|null $symbol symbol, null for all symbols
     * @return array history positions
     */
    public function getHistoryPositions(?string $symbol = null): array {
        $url = $this->baseUrl . "/api/v1/futures/position/get_history_positions";
        $params = [];
        if ($symbol) {
            $params["symbol"] = $symbol;
        }
        
        $response = $this->sendRequest('GET', $url, $params);
        return $this->handleResponse($response);
    }
}

// example usage
if (php_sapi_name() === 'cli') {
    try {
        // load config
        $config = new Config();
        
        // create client
        $client = new OpenApiHttpFuturePrivate($config);
        
        // get account info
        $account = $client->getAccount();
        echo "Account info: " . json_encode($account, JSON_PRETTY_PRINT) . "\n";
        
        // get history positions
        $historyPositions = $client->getHistoryPositions("BTCUSDT");
        echo "History positions: " . json_encode($historyPositions, JSON_PRETTY_PRINT) . "\n";
        
        // get history orders
        $historyOrders = $client->getHistoryOrders("BTCUSDT");
        echo "History orders: " . json_encode($historyOrders, JSON_PRETTY_PRINT) . "\n";
        
        /*
        WARNING!!! This is example code for placing and canceling orders. If you are using a real account,
        please be cautious when uncommenting for testing, as any financial losses will be your responsibility.
        */
        /*
        // place order example (limit order)
        $order = $client->placeOrder(
            symbol: "BTCUSDT",
            side: "BUY",
            orderType: "LIMIT",
            qty: "0.5",
            price: "60000",
            tradeSide: "OPEN",
            effect: "GTC",
            reduceOnly: false,
            clientId: date("YmdHis"),
            tpPrice: "61000",
            tpStopType: "MARK",
            tpOrderType: "LIMIT",
            tpOrderPrice: "61000.1"
        );
        echo "Place order result: " . json_encode($order, JSON_PRETTY_PRINT) . "\n";
        
        // cancel order example
        if ($order && isset($order["orderId"])) {
            $cancelResult = $client->cancelOrders("BTCUSDT", [
                ["orderId" => $order["orderId"]]
            ]);
            echo "Cancel order result: " . json_encode($cancelResult, JSON_PRETTY_PRINT) . "\n";
        }
        */
        
    } catch (Exception $e) {
        echo "Error: " . $e->getMessage() . "\n";
    }
} 