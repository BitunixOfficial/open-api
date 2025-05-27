package com.bitunix.openapi.response;


public class TpslHistoryOrderResp {

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

    private String status;

    private Long ctime;

    private Long triggerTime;

    public String getId() {
        return id;
    }

    public String getPositionId() {
        return positionId;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getBase() {
        return base;
    }

    public String getQuote() {
        return quote;
    }

    public String getTpPrice() {
        return tpPrice;
    }

    public String getTpStopType() {
        return tpStopType;
    }

    public String getSlPrice() {
        return slPrice;
    }

    public String getSlStopType() {
        return slStopType;
    }

    public String getTpOrderType() {
        return tpOrderType;
    }

    public String getTpOrderPrice() {
        return tpOrderPrice;
    }

    public String getSlOrderType() {
        return slOrderType;
    }

    public String getSlOrderPrice() {
        return slOrderPrice;
    }

    public String getTpQty() {
        return tpQty;
    }

    public String getSlQty() {
        return slQty;
    }

    public String getStatus() {
        return status;
    }

    public Long getCtime() {
        return ctime;
    }

    public Long getTriggerTime() {
        return triggerTime;
    }
}
