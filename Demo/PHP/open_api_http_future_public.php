<?php

require_once 'config.php';
require_once 'error_codes.php';
require_once 'open_api_http_sign.php';

/**
 * future public api client
 */
class OpenApiHttpFuturePublic {
    private Config $config;
    private string $baseUrl;
    private array $defaultHeaders;

    /**
     * initialize future public api client
     * 
     * @param Config $config config object, contains api_key and secret_key
     */
    public function __construct(Config $config) {
        $this->config = $config;
        $this->baseUrl = $config->getUriPrefix();
        $this->defaultHeaders = [
            'Content-Type' => 'application/json',
            'Accept' => 'application/json',
            'language' => 'en-US'
        ];
    }

    /**
     * handle response
     * 
     * @param array $response response data
     * @return array response data
     * @throws Exception when the response status code is not 200 or the business status code is not 0
     */
    private function handleResponse(array $response): array {
        if ($response['code'] !== 0) {
            $error = ErrorCode::getByCode($response['code']);
            if ($error !== null) {
                throw new Exception(ErrorCode::getMessage($response['code']));
            }
            throw new Exception("Unknown Error: {$response['code']} - {$response['msg']}");
        }
        
        return $response['data'];
    }

    /**
     * send http request
     * 
     * @param string $method request method
     * @param string $url request url
     * @param array $params request params
     * @return array response data
     * @throws Exception when the request fails
     */
    private function sendRequest(string $method, string $url, array $params = []): array {
        $headers = array_merge(
            $this->defaultHeaders
        );

        $ch = curl_init();
        $url = $this->baseUrl . $url;
        if ($method === 'GET' && !empty($params)) {
            $url .= '?' . http_build_query($params);
        }

        curl_setopt_array($ch, [
            CURLOPT_URL => $url,
            CURLOPT_RETURNTRANSFER => true,
            CURLOPT_HTTPHEADER => array_map(
                fn($k, $v) => "$k: $v",
                array_keys($headers),
                array_values($headers)
            ),
            CURLOPT_SSL_VERIFYPEER => false,
            CURLOPT_SSL_VERIFYHOST => false,
            CURLOPT_VERBOSE => false
        ]);

        if ($method === 'POST') {
            curl_setopt($ch, CURLOPT_POST, true);
            curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($params));
        }

        $response = curl_exec($ch);

        $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
        
        curl_close($ch);

        if ($httpCode !== 200) {
            throw new Exception("HTTP Error: $httpCode");
        }

        $responseData = json_decode($response, true);
        if (json_last_error() !== JSON_ERROR_NONE) {
            throw new Exception("JSON decode error: " . json_last_error_msg());
        }

        return $this->handleResponse($responseData);
    }

    /**
     * get futures trading pair market data
     * 
     * @param string|null $symbols futures trading pair, multiple separated by commas, e.g.: BTCUSDT,ETHUSDT
     * @return array market data
     */
    public function getTickers(?string $symbols = null): array {
        $params = [];
        if ($symbols !== null) {
            $params['symbols'] = $symbols;
        }
        
        return $this->sendRequest('GET', '/api/v1/futures/market/tickers', $params);
    }

    /**
     * get depth data
     * 
     * @param string $symbol futures trading pair
     * @param int $limit depth quantity, default 100
     * @return array depth data
     */
    public function getDepth(string $symbol, int $limit = 100): array {
        $params = [
            'symbol' => $symbol,
            'limit' => $limit
        ];
        
        return $this->sendRequest('GET', '/api/v1/futures/market/depth', $params);
    }

    /**
     * get kline data
     * 
     * @param string $symbol futures trading pair
     * @param string $interval kline interval, e.g.: 1m, 5m, 15m, 30m, 1h, 4h, 1d
     * @param int $limit get data quantity, default 100, max 200
     * @param int|null $startTime start time (Unix timestamp, millisecond format)
     * @param int|null $endTime end time (Unix timestamp, millisecond format)
     * @param string $type kline type, optional values: LAST_PRICE (latest price), MARK_PRICE (mark price), default: LAST_PRICE
     * @return array kline data
     */
    public function getKline(
        string $symbol,
        string $interval,
        int $limit = 100,
        ?int $startTime = null,
        ?int $endTime = null,
        string $type = 'LAST_PRICE'
    ): array {
        $params = [
            'symbol' => $symbol,
            'interval' => $interval,
            'limit' => $limit,
            'type' => $type
        ];
        
        if ($startTime !== null) {
            $params['startTime'] = $startTime;
        }
        if ($endTime !== null) {
            $params['endTime'] = $endTime;
        }
        
        return $this->sendRequest('GET', '/api/v1/futures/market/kline', $params);
    }
}

// example usage
function main() {
    try {
        // load config
        $config = new Config();
        
        // create client
        $client = new OpenApiHttpFuturePublic($config);
        
        // get market data
        $tickers = $client->getTickers("BTCUSDT,ETHUSDT");
        error_log("Tickers data: " . json_encode($tickers));
        
        // get depth data
        $depth = $client->getDepth("BTCUSDT", 5);
        error_log("Depth data: " . json_encode($depth));
        
        // get kline data
        $currentTime = round(microtime(true) * 1000); // current timestamp (millisecond)
        $oneHourAgo = $currentTime - (60 * 60 * 1000); // one hour ago timestamp
        $klines = $client->getKline(
            "BTCUSDT",
            "1m",
            5,
            $oneHourAgo,
            $currentTime,
            "LAST_PRICE"
        );
        error_log("Klines data: " . json_encode($klines));
        
    } catch (Exception $e) {
        error_log("Error in main: " . $e->getMessage());
    }
}

// if run this file directly, execute main function
if (basename(__FILE__) === basename($_SERVER['SCRIPT_FILENAME'])) {
    main();
}