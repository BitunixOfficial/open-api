package com.bitunix.openapi.request;

import com.bitunix.openapi.enums.OrderType;
import com.bitunix.openapi.enums.StopTriggerType;
import com.bitunix.openapi.enums.TpslOrderType;
import com.bitunix.openapi.enums.TradeSide;

import java.math.BigDecimal;

public class PlaceOrderRequest {

    private String marginCoin;


    private String symbol;


    private BigDecimal qty;

    private BigDecimal price;


    private String side;

    private TradeSide tradeSide;


    private OrderType orderType;

    private String positionId;

    private String effect;

    private String clientId;

    private Boolean reduceOnly = false;

    private BigDecimal tpPrice;

    private StopTriggerType tpStopType;

    private TpslOrderType tpOrderType;

    private BigDecimal tpOrderPrice;

    private BigDecimal slPrice;

    private StopTriggerType slStopType;

    private TpslOrderType slOrderType;

    private BigDecimal slOrderPrice;

    public PlaceOrderRequest() {
    }

    public String getMarginCoin() {
        return marginCoin;
    }

    public void setMarginCoin(String marginCoin) {
        this.marginCoin = marginCoin;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public TradeSide getTradeSide() {
        return tradeSide;
    }

    public void setTradeSide(TradeSide tradeSide) {
        this.tradeSide = tradeSide;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Boolean getReduceOnly() {
        return reduceOnly;
    }

    public void setReduceOnly(Boolean reduceOnly) {
        this.reduceOnly = reduceOnly;
    }

    public BigDecimal getTpPrice() {
        return tpPrice;
    }

    public void setTpPrice(BigDecimal tpPrice) {
        this.tpPrice = tpPrice;
    }

    public StopTriggerType getTpStopType() {
        return tpStopType;
    }

    public void setTpStopType(StopTriggerType tpStopType) {
        this.tpStopType = tpStopType;
    }

    public TpslOrderType getTpOrderType() {
        return tpOrderType;
    }

    public void setTpOrderType(TpslOrderType tpOrderType) {
        this.tpOrderType = tpOrderType;
    }

    public BigDecimal getTpOrderPrice() {
        return tpOrderPrice;
    }

    public void setTpOrderPrice(BigDecimal tpOrderPrice) {
        this.tpOrderPrice = tpOrderPrice;
    }

    public BigDecimal getSlPrice() {
        return slPrice;
    }

    public void setSlPrice(BigDecimal slPrice) {
        this.slPrice = slPrice;
    }

    public StopTriggerType getSlStopType() {
        return slStopType;
    }

    public void setSlStopType(StopTriggerType slStopType) {
        this.slStopType = slStopType;
    }

    public TpslOrderType getSlOrderType() {
        return slOrderType;
    }

    public void setSlOrderType(TpslOrderType slOrderType) {
        this.slOrderType = slOrderType;
    }

    public BigDecimal getSlOrderPrice() {
        return slOrderPrice;
    }

    public void setSlOrderPrice(BigDecimal slOrderPrice) {
        this.slOrderPrice = slOrderPrice;
    }
}
