package com.bitunix.openapi.response.ws;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class TickerItem {

    @JsonProperty("o")
    private BigDecimal open;
    @JsonProperty("la")
    private BigDecimal lastPrice;
    @JsonProperty("h")
    private BigDecimal high;
    @JsonProperty("l")
    private BigDecimal low;
    @JsonProperty("b")
    private BigDecimal baseVolume;
    @JsonProperty("q")
    private BigDecimal quoteVolume;
    @JsonProperty("r")
    private BigDecimal fluctuations24Hour;

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public BigDecimal getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(BigDecimal lastPrice) {
        this.lastPrice = lastPrice;
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

    public BigDecimal getBaseVolume() {
        return baseVolume;
    }

    public void setBaseVolume(BigDecimal baseVolume) {
        this.baseVolume = baseVolume;
    }

    public BigDecimal getQuoteVolume() {
        return quoteVolume;
    }

    public void setQuoteVolume(BigDecimal quoteVolume) {
        this.quoteVolume = quoteVolume;
    }

    public BigDecimal getFluctuations24Hour() {
        return fluctuations24Hour;
    }

    public void setFluctuations24Hour(BigDecimal fluctuations24Hour) {
        this.fluctuations24Hour = fluctuations24Hour;
    }
}
