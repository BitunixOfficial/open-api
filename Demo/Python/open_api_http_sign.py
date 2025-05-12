import hashlib
import time
from typing import Dict
import uuid

def get_nonce() -> str:
    """
    Generate a random string as nonce
    
    Returns:
        str: 32-character random string
    """
    return str(uuid.uuid4()).replace('-', '')

def get_timestamp() -> str:
    """
    Get current timestamp in milliseconds
    
    Returns:
        str: Millisecond timestamp
    """
    return str(int(time.time() * 1000))

def generate_signature(
    api_key: str,
    secret_key: str,
    nonce: str,
    timestamp: str,
    query_params: str = "",
    body: str = ""
) -> str:
    """
    Generate signature according to Bitunix OpenAPI doc
    Args:
        api_key: API key
        secret_key: Secret key
        nonce: Random string
        timestamp: Timestamp
        query_params: Sorted query string (no spaces)
        body: Raw JSON string (no spaces)
    Returns:
        str: Signature
    """
    digest_input = nonce + timestamp + api_key + query_params + body
    digest = hashlib.sha256(digest_input.encode('utf-8')).hexdigest()
    sign_input = digest + secret_key
    sign = hashlib.sha256(sign_input.encode('utf-8')).hexdigest()
    return sign

def get_auth_headers(
    api_key: str,
    secret_key: str,
    query_params: str = "",
    body: str = ""
) -> Dict[str, str]:
    """
    Get authentication headers
    
    Args:
        api_key: API key
        secret_key: Secret key
        query_params: Query parameters
        body: Request body
        
    Returns:
        Dict[str, str]: Authentication headers
    """
    nonce = get_nonce()
    timestamp = get_timestamp()
    
    sign = generate_signature(
        api_key=api_key,
        secret_key=secret_key,
        nonce=nonce,
        timestamp=timestamp,
        query_params=query_params,
        body=body
    )
    
    return {
        "api-key": api_key,
        "sign": sign,
        "nonce": nonce,
        "timestamp": timestamp
    }

def sort_params(params: Dict[str, str]) -> str:
    """
    Sort parameters and concatenate them
    
    Args:
        params: Parameter dictionary
        
    Returns:
        str: Sorted parameter string
    """
    if not params:
        return ""
        
    # Sort by key and concatenate directly
    return ''.join(f"{k}{v}" for k, v in sorted(params.items())) 