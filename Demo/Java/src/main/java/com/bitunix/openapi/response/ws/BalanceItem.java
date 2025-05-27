package com.bitunix.openapi.response.ws;

import java.math.BigDecimal;

public class BalanceItem {

    private String coin;

    private BigDecimal available;

    private BigDecimal frozen;

    private BigDecimal isolationFrozen;

    private BigDecimal crossFrozen;

    private BigDecimal margin;

    private BigDecimal isolationMargin;

    private BigDecimal crossMargin;

    private BigDecimal expMoney;

    public String getCoin() {
        return coin;
    }

    public BigDecimal getAvailable() {
        return available;
    }

    public BigDecimal getFrozen() {
        return frozen;
    }

    public BigDecimal getIsolationFrozen() {
        return isolationFrozen;
    }

    public BigDecimal getCrossFrozen() {
        return crossFrozen;
    }

    public BigDecimal getMargin() {
        return margin;
    }

    public BigDecimal getIsolationMargin() {
        return isolationMargin;
    }

    public BigDecimal getCrossMargin() {
        return crossMargin;
    }

    public BigDecimal getExpMoney() {
        return expMoney;
    }
}
