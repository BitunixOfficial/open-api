package main

import (
	"fmt"

	"github.com/spf13/viper"
)

// Config struct for storing configuration information
type Config struct {
	Credentials struct {
		APIKey    string `mapstructure:"api_key"`
		SecretKey string `mapstructure:"secret_key"`
	} `mapstructure:"credentials"`
	WebSocket struct {
		PublicURI         string `mapstructure:"public_uri"`
		PrivateURI        string `mapstructure:"private_uri"`
		ReconnectInterval int    `mapstructure:"reconnect_interval"`
	} `mapstructure:"websocket"`
	HTTP struct {
		URIPrefix string `mapstructure:"uri_prefix"`
	} `mapstructure:"http"`
}

// LoadConfig loads the configuration from the config.yaml file
func LoadConfig() (*Config, error) {
	viper.SetConfigName("config")
	viper.SetConfigType("yaml")
	viper.AddConfigPath("./")
	if err := viper.ReadInConfig(); err != nil {
		return nil, fmt.Errorf("failed to read config file: %w", err)
	}
	var config Config
	if err := viper.Unmarshal(&config); err != nil {
		return nil, fmt.Errorf("failed to unmarshal config: %w", err)
	}
	return &config, nil
}
