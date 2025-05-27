package com.bitunix.openapi.request;


import java.util.List;

public class CancelOrdersRequest {

    private String marginCoin;

    private String symbol;

    private List<OrderIdRequest> orderList;

    public String getMarginCoin() {
        return marginCoin;
    }

    public void setMarginCoin(String marginCoin) {
        this.marginCoin = marginCoin;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public List<OrderIdRequest> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderIdRequest> orderList) {
        this.orderList = orderList;
    }
}
