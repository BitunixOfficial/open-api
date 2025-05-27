package com.bitunix.openapi.request;


import java.util.List;

public class BatchPlaceOrderRequest {
    private String symbol;

    private List<PlaceOrderRequest> orderList;

    public BatchPlaceOrderRequest() {
    }

    public BatchPlaceOrderRequest(String symbol, List<PlaceOrderRequest> orderList) {
        this.symbol = symbol;
        this.orderList = orderList;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public List<PlaceOrderRequest> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<PlaceOrderRequest> orderList) {
        this.orderList = orderList;
    }
}
