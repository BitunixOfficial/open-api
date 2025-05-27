<?php

error_reporting(E_ALL & ~E_DEPRECATED);

require_once __DIR__ . '/vendor/autoload.php';
require_once __DIR__ . '/config.php';
require_once __DIR__ . '/open_api_ws_sign.php';

use Ratchet\Client\WebSocket;
use Ratchet\Client\Connector;
use React\EventLoop\Loop;
use React\Promise\PromiseInterface;

class OpenApiWsFuturePrivate {
    private $config;
    private $baseUrl;
    private $reconnectInterval;
    private $websocket;
    private $isConnected = false;
    private $heartbeatInterval = 3;
    private $stopPing = false;
    private $loop;
    private $connector;
    private $apiKey;
    private $secretKey;

    public function __construct(Config $config) {
        $this->config = $config;
        $this->baseUrl = $config->getPrivateWsUri();
        $this->reconnectInterval = $config->getReconnectInterval();
        $this->loop = Loop::get();
        $this->connector = new Connector($this->loop);
        $this->apiKey = $config->getApiKey();
        $this->secretKey = $config->getSecretKey();
    }

    private function sendPing() {
        if (!$this->stopPing && $this->isConnected && $this->websocket) {
            try {
                $pingData = [
                    'op' => 'ping',
                    'ping' => round(microtime(true) * 1000)
                ];
                $msg = json_encode($pingData);
                error_log("Sending ping message: " . $msg);
                $this->websocket->send($msg);
            } catch (Exception $e) {
                error_log("Error sending ping: " . $e->getMessage());
                $this->isConnected = false;
            }
        }
    }

    private function authenticate() {
        if (!$this->isConnected || !$this->websocket) {
            throw new Exception("WebSocket not connected");
        }

        try {
            $authData = get_auth_ws_future($this->apiKey, $this->secretKey);
            $msg = json_encode([
                'op' => 'login',
                'args' => [$authData]
            ]);
            $this->websocket->send($msg);
            error_log("WebSocket authentication successful");
        } catch (Exception $e) {
            error_log("Authentication failed: " . $e->getMessage());
            throw $e;
        }
    }

    private function handleMessage($message) {
        try {
            $data = json_decode($message, true);
            error_log("Received raw message: " . $message);
            
            if (isset($data['op'])) {
                switch ($data['op']) {
                    case 'pong':
                        error_log("Received pong message: " . $message);
                        return;
                    case 'connect':
                        error_log("Received connect response: " . $message);
                        return;
                }
            }

            // Define allowed private channels
            $allowedChannels = ['balance', 'position', 'order', 'tpsl'];
            if (isset($data['ch']) && in_array($data['ch'], $allowedChannels)) {
                $this->processMessage($data);
            } else {
                error_log("Received unknown message type: " . $message);
            }
        } catch (Exception $e) {
            error_log("Error handling message: " . $e->getMessage());
        }
    }

    private function processMessage($message) {
        try {
            switch ($message['ch']) {
                case 'balance':
                    $balanceData = $message['data'];
                    error_log("\n=== Balance Update ===");
                    error_log("Coin: " . ($balanceData['coin'] ?? 'N/A'));
                    error_log("Available Balance: " . ($balanceData['available'] ?? 'N/A'));
                    error_log("Frozen Amount: " . ($balanceData['frozen'] ?? 'N/A'));
                    error_log("Isolation Frozen: " . ($balanceData['isolationFrozen'] ?? 'N/A'));
                    error_log("Cross Frozen: " . ($balanceData['crossFrozen'] ?? 'N/A'));
                    error_log("Margin: " . ($balanceData['margin'] ?? 'N/A'));
                    error_log("Isolation Margin: " . ($balanceData['isolationMargin'] ?? 'N/A'));
                    error_log("Cross Margin: " . ($balanceData['crossMargin'] ?? 'N/A'));
                    error_log("Experience Money: " . ($balanceData['expMoney'] ?? 'N/A'));
                    error_log("-------------------");
                    break;
                case 'position':
                    $positionData = $message['data'];
                    error_log("\n=== Position Update ===");
                    error_log("Event: " . ($positionData['event'] ?? 'N/A'));
                    error_log("Position ID: " . ($positionData['positionId'] ?? 'N/A'));
                    error_log("Margin Mode: " . ($positionData['marginMode'] ?? 'N/A'));
                    error_log("Position Mode: " . ($positionData['positionMode'] ?? 'N/A'));
                    error_log("Side: " . ($positionData['side'] ?? 'N/A'));
                    error_log("Leverage: " . ($positionData['leverage'] ?? 'N/A'));
                    error_log("Margin: " . ($positionData['margin'] ?? 'N/A'));
                    error_log("Create Time: " . ($positionData['ctime'] ?? 'N/A'));
                    error_log("Quantity: " . ($positionData['qty'] ?? 'N/A'));
                    error_log("Entry Value: " . ($positionData['entryValue'] ?? 'N/A'));
                    error_log("Symbol: " . ($positionData['symbol'] ?? 'N/A'));
                    error_log("Realized PnL: " . ($positionData['realizedPNL'] ?? 'N/A'));
                    error_log("Unrealized PnL: " . ($positionData['unrealizedPNL'] ?? 'N/A'));
                    error_log("Funding: " . ($positionData['funding'] ?? 'N/A'));
                    error_log("Fee: " . ($positionData['fee'] ?? 'N/A'));
                    error_log("-------------------");
                    break;
                case 'order':
                    $orderData = $message['data'];
                    error_log("\n=== Order Update ===");
                    error_log("Order ID: " . ($orderData['orderId'] ?? 'N/A'));
                    error_log("Symbol: " . ($orderData['symbol'] ?? 'N/A'));
                    error_log("Type: " . ($orderData['type'] ?? 'N/A'));
                    error_log("Price: " . ($orderData['price'] ?? 'N/A'));
                    error_log("Quantity: " . ($orderData['qty'] ?? 'N/A'));
                    error_log("-------------------");
                    break;
                case 'tpsl':
                    $tpslData = $message['data'];
                    error_log("\n=== TPSL Update ===");
                    error_log("Symbol: " . ($tpslData['symbol'] ?? 'N/A'));
                    error_log("Order ID: " . ($tpslData['orderId'] ?? 'N/A'));
                    error_log("Position ID: " . ($tpslData['positionId'] ?? 'N/A'));
                    error_log("Leverage: " . ($tpslData['leverage'] ?? 'N/A'));
                    error_log("Side: " . ($tpslData['side'] ?? 'N/A'));
                    error_log("Position Mode: " . ($tpslData['positionMode'] ?? 'N/A'));
                    error_log("Type: " . ($tpslData['type'] ?? 'N/A'));
                    error_log("SL Quantity: " . ($tpslData['slQty'] ?? 'N/A'));
                    error_log("TP Order Type: " . ($tpslData['tpOrderType'] ?? 'N/A'));
                    error_log("SL Stop Type: " . ($tpslData['slStopType'] ?? 'N/A'));
                    error_log("SL Price: " . ($tpslData['slPrice'] ?? 'N/A'));
                    error_log("SL Order Price: " . ($tpslData['slOrderPrice'] ?? 'N/A'));
                    error_log("-------------------");
                    break;
            }
        } catch (Exception $e) {
            error_log("Error processing message: " . $e->getMessage());
        }
    }

    public function subscribe(array $channels) {
        if (!$this->isConnected || !$this->websocket) {
            throw new Exception("WebSocket not connected");
        }

        $msg = json_encode([
            'op' => 'subscribe',
            'args' => $channels
        ]);
        $this->websocket->send($msg);
        error_log("Private channel subscription successful");
    }

    private function connect(): PromiseInterface {
        return ($this->connector)($this->baseUrl, [], [
            'User-Agent' => 'PHP WebSocket Client'
        ])->then(
            function (WebSocket $conn) {
                $this->websocket = $conn;
                $this->isConnected = true;
                error_log("WebSocket connection successful - private");

                // Authenticate with the server
                $this->authenticate();

                // set message handler
                $conn->on('message', function ($msg) {
                    $this->handleMessage($msg);
                });

                // set close handler
                $conn->on('close', function ($code = null, $reason = null) {
                    error_log("WebSocket connection closed: $code $reason");
                    $this->isConnected = false;
                    $this->websocket = null;
                    
                    // delay reconnect
                    $this->loop->addTimer($this->reconnectInterval, function () {
                        $this->connect();
                    });
                });

                // set error handler
                $conn->on('error', function ($e) {
                    error_log("WebSocket error: " . $e->getMessage());
                    $this->isConnected = false;
                    $this->websocket = null;
                });

                // start heartbeat
                $this->startHeartbeat();

                // subscribe after connection
                $this->subscribe([
                    ["ch" => "balance"],
                    ["ch" => "position"],
                    ["ch" => "order"],
                    ["ch" => "tpsl"]
                ]);

                return $conn;
            },
            function ($e) {
                error_log("Could not connect: {$e->getMessage()}");
                $this->isConnected = false;
                $this->websocket = null;
                
                // delay reconnect
                $this->loop->addTimer($this->reconnectInterval, function () {
                    $this->connect();
                });
            }
        );
    }

    private function startHeartbeat() {
        $this->stopPing = false;
        $this->loop->addPeriodicTimer($this->heartbeatInterval, function () {
            $this->sendPing();
        });
    }

    public function start() {
        $this->connect();
        $this->loop->run();
    }
}

// Example usage
function main() {
    $config = new Config();
    $client = new OpenApiWsFuturePrivate($config);
    
    $client->start();
}

main(); 