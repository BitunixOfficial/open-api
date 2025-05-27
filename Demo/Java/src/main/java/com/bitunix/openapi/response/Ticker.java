package com.bitunix.openapi.response;

import java.math.BigDecimal;

public class Ticker {

    private String symbol;

    private BigDecimal markPrice;

    private BigDecimal lastPrice;

    private BigDecimal open;
    private BigDecimal last;
    private BigDecimal quoteVol;
    private BigDecimal baseVol;
    private BigDecimal high;
    private BigDecimal low;

    public String getSymbol() {
        return symbol;
    }

    public BigDecimal getMarkPrice() {
        return markPrice;
    }

    public BigDecimal getLastPrice() {
        return lastPrice;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public BigDecimal getLast() {
        return last;
    }

    public BigDecimal getQuoteVol() {
        return quoteVol;
    }

    public BigDecimal getBaseVol() {
        return baseVol;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public BigDecimal getLow() {
        return low;
    }
}
