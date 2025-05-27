package com.bitunix.openapi.response;


import java.math.BigDecimal;

public class OrderResp {

    private String orderId;

    private String marginCoin;

    private String symbol;

    private BigDecimal qty;

    private BigDecimal tradeQty;

    private String positionMode;

    private String marginMode;

    private Integer leverage;

    private String price;

    private BigDecimal avgPrice;

    private String side;

    private String orderType;

    private String effect;

    private String clientId;

    private Boolean reduceOnly;

    private String status;

    private BigDecimal fee;

    private BigDecimal realizedPNL;

    private BigDecimal tpPrice;

    private String tpStopType;

    private String tpOrderType;

    private BigDecimal tpOrderPrice;

    private BigDecimal slPrice;

    private String slStopType;

    private String slOrderType;

    private BigDecimal slOrderPrice;

    private Long ctime;

    private Long mtime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public BigDecimal getTradeQty() {
        return tradeQty;
    }

    public void setTradeQty(BigDecimal tradeQty) {
        this.tradeQty = tradeQty;
    }

    public String getPositionMode() {
        return positionMode;
    }

    public void setPositionMode(String positionMode) {
        this.positionMode = positionMode;
    }

    public String getMarginMode() {
        return marginMode;
    }

    public void setMarginMode(String marginMode) {
        this.marginMode = marginMode;
    }

    public Integer getLeverage() {
        return leverage;
    }

    public void setLeverage(Integer leverage) {
        this.leverage = leverage;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public BigDecimal getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(BigDecimal avgPrice) {
        this.avgPrice = avgPrice;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getRealizedPNL() {
        return realizedPNL;
    }

    public void setRealizedPNL(BigDecimal realizedPNL) {
        this.realizedPNL = realizedPNL;
    }

    public BigDecimal getTpPrice() {
        return tpPrice;
    }

    public void setTpPrice(BigDecimal tpPrice) {
        this.tpPrice = tpPrice;
    }

    public String getTpStopType() {
        return tpStopType;
    }

    public void setTpStopType(String tpStopType) {
        this.tpStopType = tpStopType;
    }

    public String getTpOrderType() {
        return tpOrderType;
    }

    public void setTpOrderType(String tpOrderType) {
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

    public String getSlStopType() {
        return slStopType;
    }

    public void setSlStopType(String slStopType) {
        this.slStopType = slStopType;
    }

    public String getSlOrderType() {
        return slOrderType;
    }

    public void setSlOrderType(String slOrderType) {
        this.slOrderType = slOrderType;
    }

    public BigDecimal getSlOrderPrice() {
        return slOrderPrice;
    }

    public void setSlOrderPrice(BigDecimal slOrderPrice) {
        this.slOrderPrice = slOrderPrice;
    }

    public Long getCtime() {
        return ctime;
    }

    public void setCtime(Long ctime) {
        this.ctime = ctime;
    }

    public Long getMtime() {
        return mtime;
    }

    public void setMtime(Long mtime) {
        this.mtime = mtime;
    }
}
