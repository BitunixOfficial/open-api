package com.bitunix.openapi.request.ws;

import com.bitunix.openapi.enums.DepthLevel;

public class DepthSubArg extends BasicSubArg{
    private String symbol;
    public DepthSubArg(DepthLevel depthLevel,String symbol) {
        super("depth_book"+depthLevel.getCount());
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
