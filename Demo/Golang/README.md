# OpenAPI SDK Demo (Golang)

This is a demonstration project for the OpenAPI SDK implementation in Golang. The project provides examples of how to interact with the Bitunix API using both HTTP and WebSocket protocols.

## Features

- HTTP API integration for public and private endpoints
- WebSocket API integration for real-time data
- Configuration management using YAML
- Error handling and response parsing
- Authentication and request signing

## Prerequisites

- Go 1.21.2 or higher
- Git

## Installation

1. Clone the repository:
```bash
git clone https://github.com/BitunixOfficial/open-api.git
cd demo/Golang
```

2. Install dependencies:
```bash
go mod download
```

## Configuration

1. Update the `config.yaml` file with your API credentials and settings.

## Usage

### Running Tests

```bash
go test -run ^TestWsFuturePublic$
go test -run ^TestWsFuturePrivate$ 
go test -run ^TestHttpFuturePublic$ 
go test -run ^TestHttpFuturePrivate$ 
```

## Project Structure

- `main_test.go` - Entry point of the application
- `config.go` - Configuration management
- `config.yaml` - Configuration file
- `open_api_http_*.go` - HTTP API implementation
- `open_api_ws_*.go` - WebSocket API implementation
- `open_api_*_sign.go` - Authentication and signing utilities
- `error_codes.go` - Error code definitions

## Dependencies

- github.com/google/uuid - UUID generation
- github.com/gorilla/websocket - WebSocket client
- github.com/spf13/viper - Configuration management

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support, please open an issue in the GitHub repository or contact the development team. 