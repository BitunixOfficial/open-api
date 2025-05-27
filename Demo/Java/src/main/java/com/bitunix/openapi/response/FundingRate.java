package com.bitunix.openapi.response;

import java.math.BigDecimal;

public class FundingRate {
    private String symbol;
    private BigDecimal markPrice;

    private BigDecimal lastPrice;

    private BigDecimal fundingRate;

    public String getSymbol() {
        return symbol;
    }

    public BigDecimal getMarkPrice() {
        return markPrice;
    }

    public BigDecimal getLastPrice() {
        return lastPrice;
    }

    public BigDecimal getFundingRate() {
        return fundingRate;
    }
}
