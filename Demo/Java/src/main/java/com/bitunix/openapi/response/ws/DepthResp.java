package com.bitunix.openapi.response.ws;

public class DepthResp extends BasicWsRespEntity<DepthItem> {

    private String symbol;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
