package com.bitunix.openapi.response;


import com.bitunix.openapi.enums.MarginMode;
import com.bitunix.openapi.enums.OrderSide;
import com.bitunix.openapi.enums.PositionMode;

import java.math.BigDecimal;

public class TradeResp {

    private String tradeId;

    private String orderId;

    private String marginCoin;

    private String symbol;

    private BigDecimal qty;

    private PositionMode positionMode;

    private MarginMode marginMode;

    private Integer leverage;

    private BigDecimal price;

    private OrderSide side;

    private String orderType;

    private String effect;

    private String clientId;

    private Boolean reduceOnly;

    private String status;

    private BigDecimal fee;

    private BigDecimal realizedPNL;

    private Long ctime;

    private String roleType;

    public String getTradeId() {
        return tradeId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getMarginCoin() {
        return marginCoin;
    }

    public String getSymbol() {
        return symbol;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public PositionMode getPositionMode() {
        return positionMode;
    }

    public MarginMode getMarginMode() {
        return marginMode;
    }

    public Integer getLeverage() {
        return leverage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OrderSide getSide() {
        return side;
    }

    public String getOrderType() {
        return orderType;
    }

    public String getEffect() {
        return effect;
    }

    public String getClientId() {
        return clientId;
    }

    public Boolean getReduceOnly() {
        return reduceOnly;
    }

    public String getStatus() {
        return status;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public BigDecimal getRealizedPNL() {
        return realizedPNL;
    }

    public Long getCtime() {
        return ctime;
    }

    public String getRoleType() {
        return roleType;
    }
}
