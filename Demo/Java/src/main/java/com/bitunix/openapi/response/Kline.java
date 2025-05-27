package com.bitunix.openapi.response;

import java.math.BigDecimal;

public class Kline {
    private BigDecimal open;

    private BigDecimal high;

    private BigDecimal low;

    private BigDecimal close;

    private BigDecimal quoteVol;

    private BigDecimal baseVol;

    private Long time;

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getClose() {
        return close;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }

    public BigDecimal getQuoteVol() {
        return quoteVol;
    }

    public void setQuoteVol(BigDecimal quoteVol) {
        this.quoteVol = quoteVol;
    }

    public BigDecimal getBaseVol() {
        return baseVol;
    }

    public void setBaseVol(BigDecimal baseVol) {
        this.baseVol = baseVol;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
