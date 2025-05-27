# OpenAPI SDK for PHP

This is the PHP implementation of the OpenAPI SDK, providing both HTTP and WebSocket clients for interacting with the exchange's API.

## Features

- HTTP API client for both public and private endpoints
- WebSocket client for both public and private channels
- Support for futures trading
- Comprehensive error handling
- Automatic reconnection for WebSocket connections
- Heartbeat mechanism for WebSocket connections

## Requirements

- PHP 7.4 or higher
- Composer
- Required PHP extensions:
  - curl
  - json
  - mbstring
  - openssl

## Installation

1. Install dependencies using Composer:
```bash
composer install
```

2. Configure your API credentials in `config.php`:
```php
class Config {
    private string $apiKey = "your_api_key";
    private string $secretKey = "your_secret_key";
    // ... other configuration
}
```

## Project Structure

```
Demo/PHP/
├── config.php                 # Configuration file
├── error_codes.php           # Error code definitions
├── open_api_http_future_private.php  # Private HTTP API client
├── open_api_http_future_public.php   # Public HTTP API client
├── open_api_http_sign.php    # HTTP request signing utilities
├── open_api_ws_future_private.php    # Private WebSocket client
├── open_api_ws_future_public.php     # Public WebSocket client
├── open_api_ws_sign.php      # WebSocket authentication utilities
└── README.md                 # This file
```

## Usage Examples

### HTTP API Client

#### Public API
```php
$config = new Config();
$client = new OpenApiHttpFuturePublic($config);

// Get market tickers
$tickers = $client->getTickers("BTCUSDT,ETHUSDT");

// Get order book depth
$depth = $client->getDepth("BTCUSDT", 100);

// Get kline data
$klines = $client->getKline("BTCUSDT", "1m", 100);
```

#### Private API
```php
$config = new Config();
$client = new OpenApiHttpFuturePrivate($config);

// Get account information
$account = $client->getAccount();

// Place an order
$order = $client->placeOrder(
    symbol: "BTCUSDT",
    side: "BUY",
    orderType: "LIMIT",
    qty: "0.5",
    price: "60000"
);

// Cancel orders
$result = $client->cancelOrders("BTCUSDT", [
    ["orderId" => "123456"]
]);
```

### WebSocket Client

#### Public WebSocket
```php
$config = new Config();
$client = new OpenApiWsFuturePublic($config);

// Start WebSocket connection and subscribe to channels
$client->start();
```

#### Private WebSocket
```php
$config = new Config();
$client = new OpenApiWsFuturePrivate($config);

// Start WebSocket connection and subscribe to private channels
$client->start();
```

## WebSocket Channels

### Public Channels
- `trade`: Real-time trade data
- `ticker`: 24-hour market data
- `depth_book1`: Order book depth data

### Private Channels
- `balance`: Account balance updates
- `position`: Position updates
- `order`: Order updates
- `tpsl`: Take profit/Stop loss updates

## Error Handling

The SDK includes comprehensive error handling:
- HTTP errors are thrown as exceptions with detailed messages
- WebSocket connection errors trigger automatic reconnection
- Business logic errors include error codes and messages

## Security

- API credentials are stored in the configuration file
- All private API requests are signed using HMAC-SHA256
- WebSocket connections are authenticated using the same signing mechanism

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request