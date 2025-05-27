package com.bitunix.openapi.request;

import java.math.BigDecimal;

public class ModifyTpslOrderRequest {

    private String orderId;

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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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
