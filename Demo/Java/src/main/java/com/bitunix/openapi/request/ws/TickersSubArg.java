package com.bitunix.openapi.request.ws;

public class TickersSubArg extends BasicSubArg {
    private String symbol;

    public TickersSubArg() {
        super("tickers");
    }

    public TickersSubArg(String symbol) {
        super("tickers");
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

}
