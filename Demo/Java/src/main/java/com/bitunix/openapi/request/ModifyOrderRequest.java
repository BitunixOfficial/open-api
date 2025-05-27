package com.bitunix.openapi.request;



import com.bitunix.openapi.enums.TpslOrderType;

import java.math.BigDecimal;

public class ModifyOrderRequest {

    private String orderId;

    private String clientId;

    private String marginCoin;

    private BigDecimal qty;

    private BigDecimal price;

    private BigDecimal tpPrice;

    private String tpStopType;

    private TpslOrderType tpOrderType;

    private BigDecimal tpOrderPrice;

    private BigDecimal slPrice;

    private String slStopType;

    private TpslOrderType slOrderType;

    private BigDecimal slOrderPrice;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getMarginCoin() {
        return marginCoin;
    }

    public void setMarginCoin(String marginCoin) {
        this.marginCoin = marginCoin;
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

    public String getSlStopType() {
        return slStopType;
    }

    public void setSlStopType(String slStopType) {
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
