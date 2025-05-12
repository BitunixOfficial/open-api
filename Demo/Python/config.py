import yaml
from typing import Dict, Any
import os

class Config:
    def __init__(self, config_path: str = "config.yaml"):
        """
        Initialize configuration manager
        
        Args:
            config_path: Configuration file path, default is config.yaml
        """
        self.config_path = config_path
        self.config_data = self._load_config()
        
    def _load_config(self) -> Dict[str, Any]:
        """
        Load configuration file
        
        Returns:
            Dict[str, Any]: Configuration data
            
        Raises:
            FileNotFoundError: Configuration file does not exist
            yaml.YAMLError: Configuration file format error
        """
        if not os.path.exists(self.config_path):
            raise FileNotFoundError(f"Configuration file does not exist: {self.config_path}")
            
        try:
            with open(self.config_path, 'r', encoding='utf-8') as f:
                return yaml.safe_load(f)
        except yaml.YAMLError as e:
            raise yaml.YAMLError(f"Configuration file format error: {e}")
            
    @property
    def api_key(self) -> str:
        """Get API Key"""
        return self.config_data.get('credentials', {}).get('api_key', '')
        
    @property
    def secret_key(self) -> str:
        """Get Secret Key"""
        return self.config_data.get('credentials', {}).get('secret_key', '')
        
    @property
    def public_ws_uri(self) -> str:
        """Get public WebSocket URI"""
        return self.config_data.get('websocket', {}).get('public_uri', '')
        
    @property
    def private_ws_uri(self) -> str:
        """Get private WebSocket URI"""
        return self.config_data.get('websocket', {}).get('private_uri', '')
    
    @property
    def uri_prefix(self) -> str:
        """Get URI prefix"""
        return self.config_data.get('http', {}).get('uri_prefix', '')
    
    @property
    def reconnect_interval(self) -> int:
        """Get reconnect interval (seconds)"""
        return self.config_data.get('websocket', {}).get('reconnect_interval', 5)
        
    def get(self, key: str, default: Any = None) -> Any:
        """
        Get configuration value
        
        Args:
            key: Configuration key, supports dot-separated nested keys, e.g.: 'websocket.public_uri'
            default: Default value, returned when the configuration does not exist
            
        Returns:
            Any: Configuration value
        """
        try:
            value = self.config_data
            for k in key.split('.'):
                value = value[k]
            return value
        except (KeyError, TypeError):
            return default 