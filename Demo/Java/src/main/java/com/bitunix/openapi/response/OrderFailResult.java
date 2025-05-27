package com.bitunix.openapi.response;

public class OrderFailResult {

    private String clientId;

    private String errorMsg;

    private Integer errorCode;

    public String getClientId() {
        return clientId;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
