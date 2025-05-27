package com.bitunix.openapi.request.ws;

public class LoginArg {

    private String apiKey;
    private Long timestamp;
    private String nonce;
    private String sign;

    public LoginArg() {
    }

    public LoginArg(String apiKey, Long timestamp, String nonce, String sign) {
        this.apiKey = apiKey;
        this.timestamp = timestamp;
        this.nonce = nonce;
        this.sign = sign;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
