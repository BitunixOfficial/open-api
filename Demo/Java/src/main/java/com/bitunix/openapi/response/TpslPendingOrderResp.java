package com.bitunix.openapi.response;


public class TpslPendingOrderResp {

    private String id;

    private String positionId;

    private String symbol;

    private String base;

    private String quote;

    private String tpPrice;

    private String tpStopType;

    private String slPrice;

    private String slStopType;

    private String tpOrderType;

    private String tpOrderPrice;

    private String slOrderType;

    private String slOrderPrice;

    private String tpQty;

    private String slQty;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getTpPrice() {
        return tpPrice;
    }

    public void setTpPrice(String tpPrice) {
        this.tpPrice = tpPrice;
    }

    public String getTpStopType() {
        return tpStopType;
    }

    public void setTpStopType(String tpStopType) {
        this.tpStopType = tpStopType;
    }

    public String getSlPrice() {
        return slPrice;
    }

    public void setSlPrice(String slPrice) {
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

    public String getTpOrderPrice() {
        return tpOrderPrice;
    }

    public void setTpOrderPrice(String tpOrderPrice) {
        this.tpOrderPrice = tpOrderPrice;
    }

    public String getSlOrderType() {
        return slOrderType;
    }

    public void setSlOrderType(String slOrderType) {
        this.slOrderType = slOrderType;
    }

    public String getSlOrderPrice() {
        return slOrderPrice;
    }

    public void setSlOrderPrice(String slOrderPrice) {
        this.slOrderPrice = slOrderPrice;
    }

    public String getTpQty() {
        return tpQty;
    }

    public void setTpQty(String tpQty) {
        this.tpQty = tpQty;
    }

    public String getSlQty() {
        return slQty;
    }

    public void setSlQty(String slQty) {
        this.slQty = slQty;
    }
}
