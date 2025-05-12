import asyncio
import json
import logging
import time
import websockets
from typing import Dict, Any, List
from Demo.Python.open_api_ws_sign import get_auth_ws_future
from config import Config

# Configure logging
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(filename)s:%(lineno)d - %(message)s')

class OpenApiWsFuturePrivate:
    def __init__(self, config: Config):
        """
        Initialize private WebSocket client
        """
        self.config = config
        self.base_url = config.private_ws_uri
        self.reconnect_interval = config.reconnect_interval
        self.message_queue = asyncio.Queue()
        self.websocket = None
        self.ping_task = None
        self.is_connected = False
        self.api_key = config.api_key
        self.secret_key = config.secret_key
        self.reconnect_count = 0
        self.max_reconnect_attempts = 5
        self.heartbeat_interval = 3  # Heartbeat interval, in seconds
        self.stop_ping = False
        
    async def _send_ping(self):
        """Send heartbeat message"""
        while not self.stop_ping:
            try:
                if self.websocket and self.is_connected:
                    msg = json.dumps({"op": "ping", "ping": int(round(time.time()))})
                    await self.websocket.send(msg)
                    logging.debug("Sent ping message")
                await asyncio.sleep(self.heartbeat_interval)
            except websockets.exceptions.ConnectionClosedError:
                logging.info("WebSocket connection closed by remote server")
                self.is_connected = False
                break
            except Exception as e:
                logging.error(f"Ping task failed: {e}")
                self.is_connected = False
                break
                
    async def _authenticate(self):
        """ Authenticate with the server """
        try:
            if not self.websocket or not self.is_connected:
                raise Exception("WebSocket not connected")
                
            auth_data = get_auth_ws_future(self.api_key, self.secret_key)
            await self.websocket.send(json.dumps({
                "op": "login",
                "args": [auth_data]
            }))
            logging.info("WebSocket authentication successful")
        except Exception as e:
            logging.error(f"Authentication failed: {e}")
            raise
            
    async def subscribe(self, channels: List[Dict[str, str]]):
        """
        Subscribe to private channels
        
        Args:
            channels: List of channels to subscribe to, e.g.:
                [
                    {"ch": "balance"},
                    {"ch": "position"},
                    {"ch": "order"}
                ]
        """
        try:
            if not self.websocket or not self.is_connected:
                raise Exception("WebSocket not connected")
                
            await self.websocket.send(json.dumps({
                "op": "subscribe",
                "args": channels
            }))
            logging.info("Private channel subscription successful")
        except Exception as e:
            logging.error(f"Private subscription failed: {e}")
            raise
            
    async def _handle_message(self, message: str):
        """Handle received messages"""
        try:
            data = json.loads(message)
            logging.info(f"Received message: {data}")

            # Handle heartbeat response
            if data.get('op') == 'ping':
                logging.debug("Received pong response")
                return

            # Define allowed private channels
            allowed_channels = ['balance', 'position', 'order', 'tpsl']
            
            if 'ch' in data and data['ch'] in allowed_channels:
                await self.message_queue.put(data)
        except json.JSONDecodeError:
            logging.error("Failed to parse message")
        except Exception as e:
            logging.error(f"Error handling message: {e}")
            
    async def _process_message(self, message: Dict[str, Any]):
        """Process messages in the message queue"""
        try:
            if message['ch'] == 'balance':
                # Handle balance data
                balance_data = message['data']
                print("\n=== Balance Update ===")
                print(f"Coin: {balance_data.get('coin', 'N/A')}")
                print(f"Available Balance: {balance_data.get('available', 'N/A')}")
                print(f"Frozen Amount: {balance_data.get('frozen', 'N/A')}")
                print(f"Isolation Frozen: {balance_data.get('isolationFrozen', 'N/A')}")
                print(f"Cross Frozen: {balance_data.get('crossFrozen', 'N/A')}")
                print(f"Margin: {balance_data.get('margin', 'N/A')}")
                print(f"Isolation Margin: {balance_data.get('isolationMargin', 'N/A')}")
                print(f"Cross Margin: {balance_data.get('crossMargin', 'N/A')}")
                print(f"Experience Money: {balance_data.get('expMoney', 'N/A')}")
                print("-------------------")
                    
            elif message['ch'] == 'position':
                # Handle position data
                position_data = message['data']
                print("\n=== Position Update ===")
                print(f"Event: {position_data.get('event', 'N/A')}")
                print(f"Position ID: {position_data.get('positionId', 'N/A')}")
                print(f"Margin Mode: {position_data.get('marginMode', 'N/A')}")
                print(f"Position Mode: {position_data.get('positionMode', 'N/A')}")
                print(f"Side: {position_data.get('side', 'N/A')}")
                print(f"Leverage: {position_data.get('leverage', 'N/A')}")
                print(f"Margin: {position_data.get('margin', 'N/A')}")
                print(f"Create Time: {position_data.get('ctime', 'N/A')}")
                print(f"Quantity: {position_data.get('qty', 'N/A')}")
                print(f"Entry Value: {position_data.get('entryValue', 'N/A')}")
                print(f"Symbol: {position_data.get('symbol', 'N/A')}")
                print(f"Realized PnL: {position_data.get('realizedPNL', 'N/A')}")
                print(f"Unrealized PnL: {position_data.get('unrealizedPNL', 'N/A')}")
                print(f"Funding: {position_data.get('funding', 'N/A')}")
                print(f"Fee: {position_data.get('fee', 'N/A')}")
                print("-------------------")
            
            elif message['ch'] == 'order':
                # Handle order data
                order_data = message['data']
                print("\n=== Order Update ===")
                print(f"Order ID: {order_data.get('orderId', 'N/A')}")
                print(f"Symbol: {order_data.get('symbol', 'N/A')}")
                print(f"Type: {order_data.get('type', 'N/A')}")
                print(f"Status: {order_data.get('status', 'N/A')}")
                print(f"Price: {order_data.get('price', 'N/A')}")
                print(f"Quantity: {order_data.get('qty', 'N/A')}")
                print("-------------------")
            
            elif message['ch'] == 'tpsl':
                # Handle tpsl data
                tpsl_data = message['data']
                print("\n=== TPSL Update ===")
                print(f"Symbol: {tpsl_data.get('symbol', 'N/A')}")
                print(f"Order ID: {tpsl_data.get('orderId', 'N/A')}")
                print(f"Position ID: {tpsl_data.get('positionId', 'N/A')}")
                print(f"Leverage: {tpsl_data.get('leverage', 'N/A')}")
                print(f"Side: {tpsl_data.get('side', 'N/A')}")
                print(f"Position Mode: {tpsl_data.get('positionMode', 'N/A')}")
                print(f"Status: {tpsl_data.get('status', 'N/A')}")
                print(f"Type: {tpsl_data.get('type', 'N/A')}")
                print(f"SL Quantity: {tpsl_data.get('slQty', 'N/A')}")
                print(f"TP Order Type: {tpsl_data.get('tpOrderType', 'N/A')}")
                print(f"SL Stop Type: {tpsl_data.get('slStopType', 'N/A')}")
                print(f"SL Price: {tpsl_data.get('slPrice', 'N/A')}")
                print(f"SL Order Price: {tpsl_data.get('slOrderPrice', 'N/A')}")
                print("-------------------")
                
        except Exception as e:
            logging.error(f"Error processing message: {e}")
            
    async def _consume_messages(self):
        """Consume message queue"""
        while True:
            message = await self.message_queue.get()
            await self._process_message(message)
            
    async def _start_ping(self):
        """Start heartbeat task"""
        if self.ping_task:
            self.ping_task.cancel()
            try:
                await self.ping_task
            except asyncio.CancelledError:
                pass
        self.stop_ping = False
        self.ping_task = asyncio.create_task(self._send_ping())
            
    async def connect(self):
        """Establish WebSocket connection"""
        reconnect_attempts = 0
        
        while reconnect_attempts < self.max_reconnect_attempts:
            try:
                async with websockets.connect(self.base_url) as websocket:
                    self.websocket = websocket
                    self.is_connected = True
                    logging.info("WebSocket connection successful - private")
                    
                    # Authenticate with the server
                    await self._authenticate()
                    
                    # Start heartbeat task
                    await self._start_ping()
                    
                    try:
                        async for message in websocket:
                            await self._handle_message(message)
                    except websockets.exceptions.ConnectionClosedError:
                        logging.info("WebSocket connection closed by remote server, attempting to reconnect...")
                    except Exception as e:
                        logging.error(f"Error processing message: {e}")
                    finally:
                        self.stop_ping = True
                        if self.ping_task:
                            self.ping_task.cancel()
                            try:
                                await self.ping_task
                            except asyncio.CancelledError:
                                pass
                    
                    self.is_connected = False
                    await asyncio.sleep(self.reconnect_interval)
                    reconnect_attempts += 1
                    logging.info(f"Attempting to reconnect... ({reconnect_attempts})")
                    
            except Exception as e:
                logging.error(f"WebSocket connection failed: {e}")
                self.is_connected = False
                await asyncio.sleep(self.reconnect_interval)
                reconnect_attempts += 1
                
    async def start(self):
        """Start WebSocket client"""
        # Start message consumption task
        consume_task = asyncio.create_task(self._consume_messages())
        
        try:
            # Start connection task
            await self.connect()
        except KeyboardInterrupt:
            logging.info("Program interrupted by user")
        except Exception as e:
            logging.error(f"Program error: {e}")
        finally:
            # Cancel all tasks
            self.stop_ping = True
            if self.ping_task:
                self.ping_task.cancel()
            consume_task.cancel()
            
            # Wait for tasks to be cancelled
            await asyncio.gather(self.ping_task, consume_task, return_exceptions=True)

async def main():
    """Main function example"""
    # Load configuration
    config = Config()
    
    # Create client
    client = OpenApiWsFuturePrivate(config)
    
    # Start client
    client_task = asyncio.create_task(client.start())
    
    # Wait for connection to be established
    await asyncio.sleep(2)
    
    # Subscribe to channels
    await client.subscribe([
        {"ch": "balance"},
        {"ch": "position"},
        {"ch": "order"}
    ])
    
    try:
        await client_task
    except KeyboardInterrupt:
        logging.info("Program interrupted by user")

if __name__ == "__main__":
    asyncio.run(main()) 