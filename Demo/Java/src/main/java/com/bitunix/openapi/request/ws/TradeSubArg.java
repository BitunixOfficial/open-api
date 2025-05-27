package com.bitunix.openapi.request.ws;

public class TradeSubArg extends BasicSubArg {
    private String symbol;

    public TradeSubArg() {
        super("trade");
    }

    public TradeSubArg(String symbol) {
        super("trade");
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

}
