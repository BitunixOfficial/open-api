package com.bitunix.openapi.response.ws;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class TickersItem {

    @JsonProperty("s")
    private String symbol;

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
    @JsonProperty("bd")
    private BigDecimal bestBidPrice;
    @JsonProperty("ak")
    private BigDecimal bestAskPrice;
    @JsonProperty("bv")
    private BigDecimal bestBidVolume;
    @JsonProperty("av")
    private BigDecimal bestAskVolume;

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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getBestBidPrice() {
        return bestBidPrice;
    }

    public void setBestBidPrice(BigDecimal bestBidPrice) {
        this.bestBidPrice = bestBidPrice;
    }

    public BigDecimal getBestAskPrice() {
        return bestAskPrice;
    }

    public void setBestAskPrice(BigDecimal bestAskPrice) {
        this.bestAskPrice = bestAskPrice;
    }

    public BigDecimal getBestBidVolume() {
        return bestBidVolume;
    }

    public void setBestBidVolume(BigDecimal bestBidVolume) {
        this.bestBidVolume = bestBidVolume;
    }

    public BigDecimal getBestAskVolume() {
        return bestAskVolume;
    }

    public void setBestAskVolume(BigDecimal bestAskVolume) {
        this.bestAskVolume = bestAskVolume;
    }
}
