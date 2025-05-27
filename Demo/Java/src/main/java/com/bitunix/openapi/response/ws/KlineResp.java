package com.bitunix.openapi.response.ws;

public class KlineResp extends BasicWsRespEntity<KlineItem> {

    private String symbol;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
