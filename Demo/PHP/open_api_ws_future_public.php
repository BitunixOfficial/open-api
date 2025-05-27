<?php

error_reporting(E_ALL & ~E_DEPRECATED);

require_once __DIR__ . '/vendor/autoload.php';
require_once __DIR__ . '/config.php';

use Ratchet\Client\WebSocket;
use Ratchet\Client\Connector;
use React\EventLoop\Loop;
use React\Promise\PromiseInterface;

class OpenApiWsFuturePublic {
    private $config;
    private $baseUrl;
    private $reconnectInterval;
    private $websocket;
    private $isConnected = false;
    private $heartbeatInterval = 3;
    private $stopPing = false;
    private $loop;
    private $connector;

    public function __construct(Config $config) {
        $this->config = $config;
        $this->baseUrl = $config->getPublicWsUri();
        $this->reconnectInterval = $config->getReconnectInterval();
        $this->loop = Loop::get();
        $this->connector = new Connector($this->loop);
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

            // Define allowed public channels
            $allowedChannels = ['depth_book1', 'trade', 'ticker'];
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
                case 'trade':
                    $tradeData = $message['data'];
                    error_log("Received trade data: " . json_encode($tradeData));
                    break;
                case 'ticker':
                    $tickerData = $message['data'];
                    error_log("Received 24h ticker: " . json_encode($tickerData));
                    break;
                case 'depth_book1':
                    $depthData = $message['data'];
                    error_log("Received order book depth: " . json_encode($depthData));
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
        error_log("Public channel subscription successful");
    }

    private function connect(): PromiseInterface {
        return ($this->connector)($this->baseUrl, [], [
            'User-Agent' => 'PHP WebSocket Client'
        ])->then(
            function (WebSocket $conn) {
                $this->websocket = $conn;
                $this->isConnected = true;
                error_log("WebSocket connection successful - public");

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
                    ["symbol" => "BTCUSDT", "ch" => "trade"],
                    ["symbol" => "BTCUSDT", "ch" => "ticker"],
                    ["symbol" => "BTCUSDT", "ch" => "depth_book1"]
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
    $client = new OpenApiWsFuturePublic($config);
    
    $client->start();
}

main(); 