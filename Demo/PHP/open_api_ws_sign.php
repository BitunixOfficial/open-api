<?php

/**
 * generate a random string as nonce
 * @return string
 */
function generate_nonce() {
    $characters = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
    $nonce = '';
    for ($i = 0; $i < 32; $i++) {
        $nonce .= $characters[rand(0, strlen($characters) - 1)];
    }
    return $nonce;
}

/**
 * generate current timestamp
 * @return string
 */
function generate_timestamp() {
    return (string)time();
}

/**
* calculate the SHA256 hash value of the input string
 * @param string $input_string
 * @return string
 */
function sha256_hex($input_string) {
    return hash('sha256', $input_string);
}

/**
 * generate authentication signature
 * @param string $nonce
 * @param string $timestamp
 * @param string $api_key
 * @param string $secret_key
 * @return string
 */
function generate_sign($nonce, $timestamp, $api_key, $secret_key) {
    $digest_input = $nonce . $timestamp . $api_key;
    $digest = sha256_hex($digest_input);
    $sign_input = $digest . $secret_key;
    return sha256_hex($sign_input);
}

/**
 * generate WebSocket authentication data
 * @param string $api_key
 * @param string $secret_key
 * @return array
 */
function get_auth_ws_future($api_key, $secret_key) {
    $nonce = generate_nonce();
    $timestamp = generate_timestamp();
    $sign = generate_sign($nonce, $timestamp, $api_key, $secret_key);
    
    return [
        'apiKey' => $api_key,
        'timestamp' => (int)$timestamp,
        'nonce' => $nonce,
        'sign' => $sign
    ];
}
