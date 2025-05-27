package com.bitunix.openapi.request.ws;

public class TickerSubArg extends BasicSubArg {
    private String symbol;

    public TickerSubArg() {
        super("ticker");
    }

    public TickerSubArg(String symbol) {
        super("ticker");
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

}
