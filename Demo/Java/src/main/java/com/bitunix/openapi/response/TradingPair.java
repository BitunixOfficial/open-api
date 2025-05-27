package com.bitunix.openapi.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TradingPair {

    private String symbol;

    private String base;

    private String quote;

    private BigDecimal minTradeVolume;

    private BigDecimal minBuyPriceOffset;

    private BigDecimal maxSellPriceOffset;

    private BigDecimal maxLimitOrderVolume;

    private BigDecimal maxMarketOrderVolume;

    private Integer basePrecision;

    private Integer quotePrecision;

    private Integer maxLeverage;

    private Integer minLeverage;

    private Integer defaultLeverage;

    private String defaultMarginMode;

    private BigDecimal priceProtectScope;

    private String symbolStatus;

    public String getSymbol() {
        return symbol;
    }

    public String getBase() {
        return base;
    }

    public String getQuote() {
        return quote;
    }

    public BigDecimal getMinTradeVolume() {
        return minTradeVolume;
    }

    public BigDecimal getMinBuyPriceOffset() {
        return minBuyPriceOffset;
    }

    public BigDecimal getMaxSellPriceOffset() {
        return maxSellPriceOffset;
    }

    public BigDecimal getMaxLimitOrderVolume() {
        return maxLimitOrderVolume;
    }

    public BigDecimal getMaxMarketOrderVolume() {
        return maxMarketOrderVolume;
    }

    public Integer getBasePrecision() {
        return basePrecision;
    }

    public Integer getQuotePrecision() {
        return quotePrecision;
    }

    public Integer getMaxLeverage() {
        return maxLeverage;
    }

    public Integer getMinLeverage() {
        return minLeverage;
    }

    public Integer getDefaultLeverage() {
        return defaultLeverage;
    }

    public String getDefaultMarginMode() {
        return defaultMarginMode;
    }

    public BigDecimal getPriceProtectScope() {
        return priceProtectScope;
    }

    public String getSymbolStatus() {
        return symbolStatus;
    }
}
