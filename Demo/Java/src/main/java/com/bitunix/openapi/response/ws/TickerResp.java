package com.bitunix.openapi.response.ws;

import java.util.List;

public class TickerResp extends BasicWsRespEntity<TickerItem>{
    private String symbol;


    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
