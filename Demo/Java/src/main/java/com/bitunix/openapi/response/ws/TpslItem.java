package com.bitunix.openapi.response.ws;

import com.bitunix.openapi.enums.*;

import java.math.BigDecimal;
import java.time.Instant;

public class TpslItem {

    private WsEvent event;

    private String orderId;

    private String positionId;

    private String symbol;

    private MarginMode positionType;

    private PositionMode positionMode;

    private OrderSide side;

    private String effect;

    private String type;

    private BigDecimal qty;

    private Boolean reductionOnly;

    private BigDecimal price;

    private Instant ctime;

    private Instant mtime;

    private Integer leverage;

    private String status;

    private String tpStopType;

    private BigDecimal tpPrice;

    private String tpOrderType;

    private BigDecimal tpOrderPrice;

    private String slStopType;

    private BigDecimal slPrice;

    private String slOrderType;

    private BigDecimal slOrderPrice;

    private BigDecimal tpQty;

    private BigDecimal slQty;

    public WsEvent getEvent() {
        return event;
    }

    public String getOrderId() {
        return orderId;
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

    public OrderSide getSide() {
        return side;
    }

    public String getEffect() {
        return effect;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public Boolean getReductionOnly() {
        return reductionOnly;
    }

    public BigDecimal getPrice() {
        return price;
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

    public String getStatus() {
        return status;
    }

    public String getTpStopType() {
        return tpStopType;
    }

    public BigDecimal getTpPrice() {
        return tpPrice;
    }

    public String getTpOrderType() {
        return tpOrderType;
    }

    public BigDecimal getTpOrderPrice() {
        return tpOrderPrice;
    }

    public String getSlStopType() {
        return slStopType;
    }

    public BigDecimal getSlPrice() {
        return slPrice;
    }

    public String getSlOrderType() {
        return slOrderType;
    }

    public BigDecimal getSlOrderPrice() {
        return slOrderPrice;
    }

    public BigDecimal getTpQty() {
        return tpQty;
    }

    public BigDecimal getSlQty() {
        return slQty;
    }
}
