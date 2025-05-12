# Authentication related functions
import hashlib
import random
import string
import time


def generate_nonce():
    """Generate a random string as nonce"""
    return ''.join(random.choices(string.ascii_letters + string.digits, k=32))

def generate_timestamp():
    """Generate current timestamp"""
    return str(int(time.time()))

def sha256_hex(input_string):
    """Calculate SHA256 hash of the input string"""
    return hashlib.sha256(input_string.encode('utf-8')).hexdigest()

def generate_sign(nonce, timestamp, api_key, secret_key):
    """Generate signature for authentication"""
    digest_input = nonce + timestamp + api_key
    digest = sha256_hex(digest_input)
    sign_input = digest + secret_key
    sign = sha256_hex(sign_input)
    return sign

def get_auth_ws_future(api_key, secret_key):
    """Generate WebSocket authentication data"""
    nonce = generate_nonce()
    timestamp = generate_timestamp()
    sign = generate_sign(nonce, timestamp, api_key, secret_key)
    
    ws_auth = {
        "apiKey": api_key,
        "timestamp": int(timestamp),
        "nonce": nonce,
        "sign": sign
    }
    return ws_auth