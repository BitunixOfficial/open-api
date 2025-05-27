package com.bitunix.openapi.response;

import com.bitunix.openapi.enums.MarginMode;

public class MarketSetting {
    private String symbol;

    private String marginCoin;

    private Integer leverage;

    private MarginMode marginMode;

    public String getSymbol() {
        return symbol;
    }

    public String getMarginCoin() {
        return marginCoin;
    }

    public Integer getLeverage() {
        return leverage;
    }

    public MarginMode getMarginMode() {
        return marginMode;
    }
}
