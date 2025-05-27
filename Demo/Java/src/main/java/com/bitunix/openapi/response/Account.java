package com.bitunix.openapi.response;
import com.bitunix.openapi.enums.PositionMode;

import java.math.BigDecimal;

public class Account {

    private String marginCoin;

    private BigDecimal available;

    private BigDecimal frozen;

    private BigDecimal margin;

    private BigDecimal transfer;

    private PositionMode positionMode;

    private BigDecimal crossUnrealizedPNL;

    private BigDecimal isolationUnrealizedPNL;

    private BigDecimal bonus;

    public String getMarginCoin() {
        return marginCoin;
    }

    public BigDecimal getAvailable() {
        return available;
    }

    public BigDecimal getFrozen() {
        return frozen;
    }

    public BigDecimal getMargin() {
        return margin;
    }

    public BigDecimal getTransfer() {
        return transfer;
    }

    public PositionMode getPositionMode() {
        return positionMode;
    }

    public BigDecimal getCrossUnrealizedPNL() {
        return crossUnrealizedPNL;
    }

    public BigDecimal getIsolationUnrealizedPNL() {
        return isolationUnrealizedPNL;
    }

    public BigDecimal getBonus() {
        return bonus;
    }
}
