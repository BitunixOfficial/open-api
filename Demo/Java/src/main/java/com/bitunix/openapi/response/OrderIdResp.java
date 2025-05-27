package com.bitunix.openapi.response;

public class OrderIdResp {

    private String orderId;

    private String clientId;

    public OrderIdResp() {
    }


    public OrderIdResp(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
