package com.bitunix.openapi.request;


import java.math.BigDecimal;

public class PlaceTpslOrderRequest {

    private String symbol;

    private String positionId;

    private BigDecimal tpPrice;

    private String tpStopType;

    private BigDecimal slPrice;

    private String slStopType;

    private String tpOrderType;

    private BigDecimal tpOrderPrice;

    private String slOrderType;

    private BigDecimal slOrderPrice;

    private BigDecimal tpQty;

    private BigDecimal slQty;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
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

    public BigDecimal getTpQty() {
        return tpQty;
    }

    public void setTpQty(BigDecimal tpQty) {
        this.tpQty = tpQty;
    }

    public BigDecimal getSlQty() {
        return slQty;
    }

    public void setSlQty(BigDecimal slQty) {
        this.slQty = slQty;
    }
}
