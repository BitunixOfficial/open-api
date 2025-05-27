package com.bitunix.openapi.response.ws;

import com.bitunix.openapi.enums.*;

import java.math.BigDecimal;
import java.time.Instant;

public class PositionItem {

    private WsEvent event;

    private String positionId;

    private String symbol;

    private MarginMode positionType;

    private PositionMode positionMode;

    private PositionSide side;

    private BigDecimal margin;

    private BigDecimal qty;

    private BigDecimal entryValue;

    private Instant ctime;

    private Instant mtime;

    private Integer leverage;

    private BigDecimal realizedPNL;

    private BigDecimal unrealizedPNL;

    private BigDecimal funding;

    private BigDecimal fee;

    public WsEvent getEvent() {
        return event;
    }

    public String getPositionId() {
        return positionId;
    }

    public String getSymbol() {
        return symbol;
    }

    public MarginMode getPositionType() {
        return positionType;
    }

    public PositionMode getPositionMode() {
        return positionMode;
    }

    public PositionSide getSide() {
        return side;
    }

    public BigDecimal getMargin() {
        return margin;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public BigDecimal getEntryValue() {
        return entryValue;
    }

    public Instant getCtime() {
        return ctime;
    }

    public Instant getMtime() {
        return mtime;
    }

    public Integer getLeverage() {
        return leverage;
    }

    public BigDecimal getRealizedPNL() {
        return realizedPNL;
    }

    public BigDecimal getUnrealizedPNL() {
        return unrealizedPNL;
    }

    public BigDecimal getFunding() {
        return funding;
    }

    public BigDecimal getFee() {
        return fee;
    }
}
