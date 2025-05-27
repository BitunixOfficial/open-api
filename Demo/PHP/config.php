<?php

class Config {
    private string $configPath;
    private array $configData;

    /**
     * initialize config manager
     * 
     * @param string $configPath config file path, default is config.json
     */
    public function __construct(string $configPath = "config.json") {
        $this->configPath = $configPath;
        $this->configData = $this->loadConfig();
    }

    /**
     * load config file
     * 
     * @return array config data
     * @throws Exception when config file does not exist or format error
     */
    private function loadConfig(): array {
        if (!file_exists($this->configPath)) {
            throw new Exception("Configuration file does not exist: {$this->configPath}");
        }

        try {
            $jsonContent = file_get_contents($this->configPath);
            $config = json_decode($jsonContent, true);
            
            if (json_last_error() !== JSON_ERROR_NONE) {
                throw new Exception("JSON decode error: " . json_last_error_msg());
            }
            
            return $config;
        } catch (Exception $e) {
            throw new Exception("Configuration file format error: " . $e->getMessage());
        }
    }

    /**
     * get API key
     * 
     * @return string
     */
    public function getApiKey(): string {
        return $this->configData['credentials']['api_key'] ?? '';
    }

    /**
     * get secret key
     * 
     * @return string
     */
    public function getSecretKey(): string {
        return $this->configData['credentials']['secret_key'] ?? '';
    }

    /**
     * get public websocket uri
     * 
     * @return string
     */
    public function getPublicWsUri(): string {
        return $this->configData['websocket']['public_uri'] ?? '';
    }

    /**
     * get private websocket uri
     * 
     * @return string
     */
    public function getPrivateWsUri(): string {
        return $this->configData['websocket']['private_uri'] ?? '';
    }

    /**
     * get uri prefix
     * 
     * @return string
     */
    public function getUriPrefix(): string {
        return $this->configData['http']['uri_prefix'] ?? '';
    }

    /**
     * get reconnect interval (seconds)
     * 
     * @return int
     */
    public function getReconnectInterval(): int {
        return $this->configData['websocket']['reconnect_interval'] ?? 5;
    }

    /**
     * get config value
     * 
     * @param string $key config key, support dot separated nested keys, e.g. 'websocket.public_uri'
     * @param mixed $default default value, return when config does not exist
     * @return mixed config value
     */
    public function get(string $key, $default = null) {
        $keys = explode('.', $key);
        $value = $this->configData;

        foreach ($keys as $k) {
            if (!isset($value[$k])) {
                return $default;
            }
            $value = $value[$k];
        }

        return $value;
    }
} 