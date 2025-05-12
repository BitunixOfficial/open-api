import json
from typing import Dict, Optional, Any, List
import requests
from config import Config
from error_codes import ErrorCode
from open_api_http_sign import get_auth_headers, sort_params
import logging
import asyncio

# Configure logging
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(filename)s:%(lineno)d - %(message)s')

class OpenApiHttpFuturePrivate:
    def __init__(self, config: Config):
        """
        Initialize OpenApiHttpFuturePrivate class
        
        Args:
            config: Configuration object containing api_key and secret_key
        """
        self.config = config
        self.api_key = config.api_key
        self.secret_key = config.secret_key
        self.base_url = config.uri_prefix
        self.session = requests.Session()
        
        # Set common request headers
        self.session.headers.update({
            "language": "en-US",
            "Content-Type": "application/json"
        })
        
    def _handle_response(self, response: requests.Response) -> Dict[str, Any]:
        """
        Handle response
        
        Args:
            response: Response object
            
        Returns:
            Dict[str, Any]: Response data
            
        Raises:
            Exception: When response status code is not 200 or business status code is not 0
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
    
    def get_account(self, margin_coin: str = "USDT") -> Dict[str, Any]:
        """
        Get account information
        
        Args:
            margin_coin: Margin currency, default USDT
            
        Returns:
            Dict[str, Any]: Account information
        """
        url = f"{self.base_url}/api/v1/futures/account"
        params = {
            "marginCoin": margin_coin
        }
        
        query_string = sort_params(params)
        headers = get_auth_headers(self.api_key, self.secret_key, query_string)
        
        response = self.session.get(url, params=params, headers=headers)
        return self._handle_response(response)
    
    def place_order(self, symbol: str, side: str, order_type: str, qty: str, 
                   price: Optional[str] = None, position_id: Optional[str] = None,
                   trade_side: str = "OPEN", effect: str = "GTC", reduce_only: bool = False,
                   client_id: Optional[str] = None, tp_price: Optional[str] = None,
                   tp_stop_type: Optional[str] = None, tp_order_type: Optional[str] = None,
                   tp_order_price: Optional[str] = None) -> Dict[str, Any]:
        """
        Place order
        
        Args:
            symbol: Trading pair
            side: Direction, BUY or SELL
            order_type: Order type, LIMIT or MARKET
            qty: Quantity
            price: Price, required when order_type is LIMIT
            position_id: Position ID
            trade_side: Trading direction, OPEN (open position) or CLOSE (close position), default OPEN
            effect: Order validity period, default GTC
            reduce_only: Whether to reduce position only
            client_id: Client order ID
            tp_price: Take profit price
            tp_stop_type: Take profit trigger type, MARK (mark price) or LAST (last price)
            tp_order_type: Take profit order type, LIMIT or MARKET
            tp_order_price: Take profit order price
            
        Returns:
            Dict[str, Any]: Order information
        """
        url = f"{self.base_url}/api/v1/futures/trade/place_order"
        
        data = {
            "symbol": symbol,
            "side": side,
            "orderType": order_type,
            "qty": qty,
            "tradeSide": trade_side,
            "effect": effect,
            "reduceOnly": reduce_only
        }
        
        if price is not None:
            data["price"] = price
        if position_id is not None:
            data["positionId"] = position_id
        if client_id is not None:
            data["clientId"] = client_id
        if tp_price is not None:
            data["tpPrice"] = tp_price
        if tp_stop_type is not None:
            data["tpStopType"] = tp_stop_type
        if tp_order_type is not None:
            data["tpOrderType"] = tp_order_type
        if tp_order_price is not None:
            data["tpOrderPrice"] = tp_order_price
            
        body = json.dumps(data)
        headers = get_auth_headers(self.api_key, self.secret_key, body=body)
        
        response = self.session.post(url, json=data, headers=headers)
        return self._handle_response(response)
    
    def cancel_orders(self, symbol: str, order_list: List[Dict[str, str]]) -> Dict[str, Any]:
        """
        Cancel multiple orders
        
        Args:
            symbol: Trading pair
            order_list: List of orders, each containing orderId or clientId
            
        Returns:
            Dict[str, Any]: Cancellation result
        """
        url = f"{self.base_url}/api/v1/futures/trade/cancel_orders"
        
        data = {
            "symbol": symbol,
            "orderList": order_list
        }
            
        body = json.dumps(data)
        headers = get_auth_headers(self.api_key, self.secret_key, body=body)
        
        response = self.session.post(url, json=data, headers=headers)
        return self._handle_response(response)
    
    def get_history_orders(self, symbol: Optional[str] = None) -> Dict[str, Any]:
        """
        Get historical orders
        
        Args:
            symbol: Trading pair, if not provided, get orders for all trading pairs
            
        Returns:
            Dict[str, Any]: Historical order list
        """
        url = f"{self.base_url}/api/v1/futures/trade/get_history_orders"
        params = {}
        if symbol:
            params["symbol"] = symbol
            
        query_string = sort_params(params)
        headers = get_auth_headers(self.api_key, self.secret_key, query_string)
        
        response = self.session.get(url, params=params, headers=headers)
        return self._handle_response(response)

    def get_history_positions(self, symbol: Optional[str] = None) -> Dict[str, Any]:
        """
        Get historical position information
        
        Args:
            symbol: Trading pair, if not provided, get all positions
            
        Returns:
            Dict[str, Any]: Historical position information
        """
        url = f"{self.base_url}/api/v1/futures/position/get_history_positions"
        params = {}
        if symbol:
            params["symbol"] = symbol
            
        query_string = sort_params(params)
        headers = get_auth_headers(self.api_key, self.secret_key, query_string)
        
        response = self.session.get(url, params=params, headers=headers)
        return self._handle_response(response)

async def main():
    """Main function example"""
    # Load configuration
    config = Config()
    
    # Create client
    client = OpenApiHttpFuturePrivate(config)
    
    try:
        # Get account information
        account = client.get_account()
        logging.info(f"Account info: {account}")
        
        # Get historical position information
        history_positions = client.get_history_positions("BTCUSDT")
        logging.info(f"History positions: {history_positions}")
        
        # Get historical orders
        history_orders = client.get_history_orders("BTCUSDT")
        logging.info(f"History orders: {history_orders}")

        """
        WARNING!!! This is example code for placing and canceling orders. If you are using a real account,
        please be cautious when uncommenting for testing, as any financial losses will be your responsibility.
        """
        # # Example order placement (limit order)
        # order = client.place_order(
        #     symbol="BTCUSDT",
        #     side="BUY",
        #     order_type="LIMIT",
        #     qty="0.5",
        #     price="60000",
        #     trade_side="OPEN",
        #     effect="GTC",
        #     reduce_only=False,
        #     client_id=time.strftime("%Y%m%d%H%M%S", time.localtime()),
        #     tp_price="61000",
        #     tp_stop_type="MARK",
        #     tp_order_type="LIMIT",
        #     tp_order_price="61000.1"
        # )
        # logging.info(f"Place order result: {order}")
        #
        # # Example order cancellation
        # if order and "orderId" in order:
        #     cancel_result = client.cancel_orders("BTCUSDT", [
        #         {"orderId": order["orderId"]},
        #         {"clientId": order["clientId"]}
        #     ])
        #     logging.info(f"Cancel orders result: {cancel_result}")
        
    except Exception as e:
        logging.error(f"Error in main: {e}")

if __name__ == "__main__":
    asyncio.run(main()) 