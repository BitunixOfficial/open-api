from typing import Dict, Optional, Any
import requests
from config import Config
from error_codes import ErrorCode
from open_api_http_sign import get_auth_headers, sort_params
import logging
import asyncio
import time

# Configure logging
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(filename)s:%(lineno)d - %(message)s')

class OpenApiHttpFuturePublic:
    def __init__(self, config: Config):
        """
        Initialize OpenApiHttpFuturePublic class
        
        Args:
            config: Configuration object, containing api_key and secret_key
        """
        self.config = config
        self.base_url = config.uri_prefix
        self.session = requests.Session()
        
    def _handle_response(self, response: requests.Response) -> Dict[str, Any]:
        """
        Handle response
        
        Args:
            response: Response object
            
        Returns:
            Dict[str, Any]: Response data
            
        Raises:
            Exception: When the response status code is not 200 or the business status code is not 0
        """
        if response.status_code != 200:
            raise Exception(f"HTTP Error: {response.status_code}")
        
        data = response.json()
        if data["code"] != 0:
            error = ErrorCode.get_by_code(data["code"])
            if error:
                raise Exception(str(error))
            raise Exception(f"Unknown Error: {data['code']} - {data['msg']}")
        
        return data["data"]
    
    def get_tickers(self, symbols: Optional[str] = None) -> Dict[str, Any]:
        """
        Get futures trading pair market data
        
        Args:
            symbols: Futures trading pair, multiple separated by commas, e.g.: BTCUSDT,ETHUSDT
            
        Returns:
            Dict[str, Any]: Market data
        """
        url = f"{self.base_url}/api/v1/futures/market/tickers"
        params = {}
        if symbols:
            params["symbols"] = symbols
            
        query_string = sort_params(params)
        headers = get_auth_headers(self.config.api_key, self.config.secret_key, query_string)
        
        response = self.session.get(url, params=params, headers=headers)
        return self._handle_response(response)
    
    def get_depth(self, symbol: str, limit: int = 100) -> Dict[str, Any]:
        """
        Get depth data
        
        Args:
            symbol: Futures trading pair
            limit: Depth quantity, default 100
            
        Returns:
            Dict[str, Any]: Depth data
        """
        url = f"{self.base_url}/api/v1/futures/market/depth"
        params = {
            "symbol": symbol,
            "limit": limit
        }
        
        query_string = sort_params(params)
        headers = get_auth_headers(self.config.api_key, self.config.secret_key, query_string)
        
        response = self.session.get(url, params=params, headers=headers)
        return self._handle_response(response)
    
    def get_kline(self, symbol: str, interval: str, limit: int = 100, start_time: Optional[int] = None, end_time: Optional[int] = None, type: str = "LAST_PRICE") -> Dict[str, Any]:
        """
        Get K-line data
        
        Args:
            symbol: Futures trading pair
            interval: K-line interval, e.g.: 1m, 5m, 15m, 30m, 1h, 4h, 1d
            limit: Number of data to get, default 100, maximum 200
            start_time: Start time (Unix timestamp, milliseconds format)
            end_time: End time (Unix timestamp, milliseconds format)
            type: K-line type, optional values: LAST_PRICE (latest price), MARK_PRICE (mark price), default: LAST_PRICE
            
        Returns:
            Dict[str, Any]: K-line data
        """
        url = f"{self.base_url}/api/v1/futures/market/kline"
        params = {
            "symbol": symbol,
            "interval": interval,
            "limit": limit,
            "type": type
        }
        
        if start_time is not None:
            params["startTime"] = start_time
        if end_time is not None:
            params["endTime"] = end_time
            
        query_string = sort_params(params)
        headers = get_auth_headers(self.config.api_key, self.config.secret_key, query_string)
        
        response = self.session.get(url, params=params, headers=headers)
        return self._handle_response(response)

async def main():
    """Main function example"""
    # Load configuration
    config = Config()
    
    # Create client
    client = OpenApiHttpFuturePublic(config)
    
    try:
        # Get market data
        tickers = client.get_tickers("BTCUSDT,ETHUSDT")
        logging.info(f"Tickers data: {tickers}")
        
        # Get depth data
        depth = client.get_depth("BTCUSDT", 5)
        logging.info(f"Depth data: {depth}")
        
        # Get K-line data
        current_time = int(time.time() * 1000)  # Current timestamp (milliseconds)
        one_hour_ago = current_time - (60 * 60 * 1000)  # One hour ago timestamp
        klines = client.get_kline("BTCUSDT", "1m", 5, start_time=one_hour_ago, end_time=current_time, type="LAST_PRICE")
        logging.info(f"Klines data: {klines}")
        
    except Exception as e:
        logging.error(f"Error in main: {e}")

if __name__ == "__main__":
    asyncio.run(main()) 