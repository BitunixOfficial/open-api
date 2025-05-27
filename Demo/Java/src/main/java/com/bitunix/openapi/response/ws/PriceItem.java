package com.bitunix.openapi.response.ws;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class PriceItem {

    @JsonProperty("ip")
    private BigDecimal indexPrice;
    @JsonProperty("mp")
    private BigDecimal markPrice;
    @JsonProperty("fr")
    private BigDecimal fundingRate;
    @JsonProperty("ft")
    private String fundingRateSettlementTime;
    @JsonProperty("nft")
    private String nextFundingRateSettlementTime;

    public BigDecimal getIndexPrice() {
        return indexPrice;
    }

    public BigDecimal getMarkPrice() {
        return markPrice;
    }

    public BigDecimal getFundingRate() {
        return fundingRate;
    }

    public String getFundingRateSettlementTime() {
        return fundingRateSettlementTime;
    }

    public String getNextFundingRateSettlementTime() {
        return nextFundingRateSettlementTime;
    }
}
