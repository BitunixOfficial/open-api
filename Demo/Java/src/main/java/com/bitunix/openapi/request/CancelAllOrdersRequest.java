package com.bitunix.openapi.request;

public class CancelAllOrdersRequest {

    private String marginCoin;

    private String symbol;


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
}
