package com.bitunix.openapi.request;

import com.bitunix.openapi.enums.MarginMode;

public class ChangeMarginMode {
    private MarginMode marginMode;

    private String symbol;

    private String marginCoin;

    public MarginMode getMarginMode() {
        return marginMode;
    }

    public void setMarginMode(MarginMode marginMode) {
        this.marginMode = marginMode;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getMarginCoin() {
        return marginCoin;
    }

    public void setMarginCoin(String marginCoin) {
        this.marginCoin = marginCoin;
    }
}
