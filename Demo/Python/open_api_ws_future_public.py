import asyncio
import json
import logging
import time
import websockets
from typing import Dict, Any, List
from config import Config

# Configure logging
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(filename)s:%(lineno)d - %(message)s')

class OpenApiWsFuturePublic:
    def __init__(self, config: Config):
        """
        Initialize OpenApiWsFuturePublic class
        """
        self.config = config
        self.base_url = config.public_ws_uri
        self.reconnect_interval = config.reconnect_interval
        self.message_queue = asyncio.Queue()
        self.websocket = None
        self.ping_task = None
        self.is_connected = False
        self.stop_ping = False
        self.heartbeat_interval = 3  # Heartbeat interval, in seconds
        
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
        
    async def subscribe(self, channels: List[Dict[str, str]]):
        """
        Subscribe to public channels
        
        Args:
            channels: List of channels to subscribe to, e.g.:
                [
                    {"symbol": "BTCUSDT", "ch": "trade"},
                    {"symbol": "BTCUSDT", "ch": "ticker"},
                    {"symbol": "BTCUSDT", "ch": "depth_book1"}
                ]
        """
        try:
            if not self.websocket or not self.is_connected:
                raise Exception("WebSocket not connected")
                
            await self.websocket.send(json.dumps({
                "op": "subscribe",
                "args": channels
            }))
            logging.info("Public channel subscription successful")
        except Exception as e:
            logging.error(f"Public subscription failed: {e}")
            raise
            
    async def _handle_message(self, message: str):
        """Handle received messages"""
        try:
            data = json.loads(message)

            # Handle heartbeat response
            if data.get('op') == 'ping':
                logging.info(f"Received message: {data}")
                return

            # Define allowed public channels
            allowed_channels = ['depth_book1', 'trade', 'ticker']
            if 'ch' in data and data['ch'] in allowed_channels:
                await self.message_queue.put(data)
        except json.JSONDecodeError:
            logging.error("Failed to parse message")
        except Exception as e:
            logging.error(f"Error handling message: {e}")
            
    async def _process_message(self, message: Dict[str, Any]):
        """Process messages in the message queue"""
        try:
            if message['ch'] == 'trade':
                # Handle real-time trade data
                trade_data = message['data']
                logging.info(f"Received trade data: {trade_data}")
                
            elif message['ch'] == 'ticker':
                # Handle 24-hour market data
                ticker_data = message['data']
                logging.info(f"Received 24h ticker: {ticker_data}")
                
            elif message['ch'] == 'depth_book1':
                # Handle order book depth data
                depth_data = message['data']
                logging.info(f"Received order book depth: {depth_data}")
                
        except Exception as e:
            logging.error(f"Error processing message: {e}")
            
    async def _consume_messages(self):
        """Consume message queue"""
        while True:
            message = await self.message_queue.get()
            await self._process_message(message)
            
    async def connect(self):
        """Establish WebSocket connection"""
        reconnect_attempts = 0
        
        while True:
            try:
                async with websockets.connect(self.base_url) as websocket:
                    self.websocket = websocket
                    self.is_connected = True
                    logging.info("WebSocket connection successful - public")
                    
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
    client = OpenApiWsFuturePublic(config)
    
    # Start client
    client_task = asyncio.create_task(client.start())
    
    # Wait for connection to be established
    await asyncio.sleep(2)
    
    # Subscribe to channels
    await client.subscribe([
        {"symbol": "BTCUSDT", "ch": "trade"},
        {"symbol": "BTCUSDT", "ch": "ticker"},
        {"symbol": "BTCUSDT", "ch": "depth_book1"}
    ])
    
    try:
        await client_task
    except KeyboardInterrupt:
        logging.info("Program interrupted by user")

if __name__ == "__main__":
    asyncio.run(main()) 