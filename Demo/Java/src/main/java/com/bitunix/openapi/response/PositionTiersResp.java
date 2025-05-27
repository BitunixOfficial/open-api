package com.bitunix.openapi.response;


import java.math.BigDecimal;

public class PositionTiersResp {

    private String symbol;

    private Integer level;

    private BigDecimal startValue;

    private BigDecimal endValue;

    private Integer leverage;

    private BigDecimal maintenanceMarginRate;

    public String getSymbol() {
        return symbol;
    }

    public Integer getLevel() {
        return level;
    }

    public BigDecimal getStartValue() {
        return startValue;
    }

    public BigDecimal getEndValue() {
        return endValue;
    }

    public Integer getLeverage() {
        return leverage;
    }

    public BigDecimal getMaintenanceMarginRate() {
        return maintenanceMarginRate;
    }
}
