# Open API Python Demo

This is a Python example project demonstrating how to use the Open API. The project includes sample code for both WebSocket and HTTP interfaces.

## Environment Requirements

- Python 3.9 or higher
- pip package manager

## Installation Steps

1. Clone the project to your local machine
2. Navigate to the Python example directory:
   ```bash
   cd Demo/Python
   ```
3. Install dependencies:
   ```bash
   python3 -m pip install -r requirements.txt
   ```

## Configuration File

Before using the example code, you need to configure the `config.py` file with the following information:
- API Key
- Secret Key
- API Address

## Example Code Description

### WebSocket Private Interface Example
- File: `open_api_ws_future_private.py`
- Functionality: Subscribe to private channels such as account balance, positions, and orders
- Run command:
  ```bash
  python3 open_api_ws_future_private.py
  ```

### HTTP Public Interface Example
- File: `open_api_http_future_public.py`
- Functionality: Retrieve public data such as market data, order book depth, and K-lines
- Run command:
  ```bash
  python3 open_api_http_future_public.py
  ```

## Notes

1. Ensure your network environment can access the API server normally
2. It is recommended to test in a test environment first
3. Keep your API Key and Secret Key secure and do not share them with others
4. If you encounter issues, check the log output for troubleshooting

## Common Issues

1. If you encounter `ModuleNotFoundError`, check if all dependencies are correctly installed
2. If the connection fails, check your network environment and API address configuration
3. If authentication fails, verify that your API Key and Secret Key are correct
4. If the connection fails, try increasing the wait time for "connection establishment":
   ```await asyncio.sleep(2)```

## Logging Information

- Log Level: INFO
- Log Format: Time - Level - Filename:Line Number - Message
- Log Output: Console 