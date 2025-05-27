<?php

/**
 * OpenAPI HTTP signature tool class
 */
class OpenApiHttpSign {
    /**
     * generate a random string as nonce
     * 
     * @return string 32-bit random string
     */
    public static function getNonce(): string {
        return md5(uniqid(mt_rand(), true));
    }

    /**
     * get current timestamp (millisecond)
     * 
     * @return string millisecond timestamp
     */
    public static function getTimestamp(): string {
        return (string)(round(microtime(true) * 1000));
    }

    /**
     * generate signature
     * 
     * @param string $apiKey API key
     * @param string $secretKey secret key
     * @param string $nonce random string
     * @param string $timestamp timestamp
     * @param string $queryParams sorted query params string (no spaces)
     * @param string $body original JSON string (no spaces)
     * @return string signature
     */
    public static function generateSignature(
        string $apiKey,
        string $secretKey,
        string $nonce,
        string $timestamp,
        string $queryParams = "",
        string $body = ""
    ): string {
        $digestInput = $nonce . $timestamp . $apiKey . $queryParams . $body;
        $digest = hash('sha256', $digestInput);
        $signInput = $digest . $secretKey;
        return hash('sha256', $signInput);
    }

    /**
     * get authentication header information
     * 
     * @param string $apiKey API key
     * @param string $secretKey secret key
     * @param array $queryParams query params
     * @param string $body request body
     * @return array authentication header information
     */
    public static function getAuthHeaders(
        string $apiKey,
        string $secretKey,
        array $queryParams = [],
        string $body = ""
    ): array {
        $nonce = self::getNonce();
        $timestamp = self::getTimestamp();
        $queryParamsStr = self::sortParams($queryParams);

        $sign = self::generateSignature(
            apiKey: $apiKey,
            secretKey: $secretKey,
            nonce: $nonce,
            timestamp: $timestamp,
            queryParams: $queryParamsStr,
            body: $body
        );
        
        return [
            "api-key" => $apiKey,
            "sign" => $sign,
            "nonce" => $nonce,
            "timestamp" => $timestamp
        ];
    }

    /**
     * sort parameters and concatenate
     * 
     * @param array $params parameter dictionary
     * @return string sorted parameter string
     */
    public static function sortParams(array $params): string {
        if (empty($params)) {
            return "";
        }
        
        // sort by key and concatenate directly
        ksort($params);
        return implode('', array_map(
            fn($k, $v) => $k . $v,
            array_keys($params),
            array_values($params)
        ));
    }
} 