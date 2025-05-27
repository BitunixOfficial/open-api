package com.bitunix.openapi.request.ws;

public class PriceSubArg extends BasicSubArg {
    private String symbol;

    public PriceSubArg() {
        super("price");
    }

    public PriceSubArg(String symbol) {
        super("price");
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

}
