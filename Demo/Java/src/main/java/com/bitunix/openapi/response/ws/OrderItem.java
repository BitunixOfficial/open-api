package com.bitunix.openapi.response.ws;

import com.bitunix.openapi.enums.*;

import java.math.BigDecimal;
import java.time.Instant;

public class OrderItem {

    private WsEvent event;

    private String orderId;

    private String symbol;

    private MarginMode positionType;

    private PositionMode positionMode;

    private OrderSide side;

    private String effect;

    private String type;

    private BigDecimal qty;

    private Boolean reductionOnly = Boolean.FALSE;

    private BigDecimal price;

    private Instant ctime;

    private Instant mtime;

    private Integer leverage;

    private OrderStatus orderStatus;

    private BigDecimal fee;

    private StopTriggerType tpStopType;

    private BigDecimal tpPrice;

    private OrderType tpOrderType;

    private BigDecimal tpOrderPrice;

    private StopTriggerType slStopType;

    private BigDecimal slPrice;

    private OrderType slOrderType;

    private BigDecimal slOrderPrice;

    public WsEvent getEvent() {
        return event;
    }

    public String getOrderId() {
        return orderId;
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

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public StopTriggerType getTpStopType() {
        return tpStopType;
    }

    public BigDecimal getTpPrice() {
        return tpPrice;
    }

    public OrderType getTpOrderType() {
        return tpOrderType;
    }

    public BigDecimal getTpOrderPrice() {
        return tpOrderPrice;
    }

    public StopTriggerType getSlStopType() {
        return slStopType;
    }

    public BigDecimal getSlPrice() {
        return slPrice;
    }

    public OrderType getSlOrderType() {
        return slOrderType;
    }

    public BigDecimal getSlOrderPrice() {
        return slOrderPrice;
    }

    public void setReductionOnly(Boolean reductionOnly) {
        this.reductionOnly = reductionOnly;
    }
}
