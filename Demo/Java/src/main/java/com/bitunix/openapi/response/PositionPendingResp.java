package com.bitunix.openapi.response;

import com.bitunix.openapi.enums.MarginMode;
import com.bitunix.openapi.enums.PositionMode;

public class PositionPendingResp {

    private String positionId;

    private String symbol;

    private String marginCoin;

    private String qty;

    private String entryValue;

    private String side;

    private MarginMode marginMode;

    private PositionMode positionMode;

    private Integer leverage;

    private String fee;

    private String funding;

    private String realizedPNL;

    private String margin;

    private String unrealizedPNL;

    private String liqPrice;

    private String avgOpenPrice;

    private String marginRate;

    private Long ctime;

    private Long mtime;

    public String getPositionId() {
        return positionId;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getMarginCoin() {
        return marginCoin;
    }

    public String getQty() {
        return qty;
    }

    public String getEntryValue() {
        return entryValue;
    }

    public String getSide() {
        return side;
    }

    public MarginMode getMarginMode() {
        return marginMode;
    }

    public PositionMode getPositionMode() {
        return positionMode;
    }

    public Integer getLeverage() {
        return leverage;
    }

    public String getFee() {
        return fee;
    }

    public String getFunding() {
        return funding;
    }

    public String getRealizedPNL() {
        return realizedPNL;
    }

    public String getMargin() {
        return margin;
    }

    public String getUnrealizedPNL() {
        return unrealizedPNL;
    }

    public String getLiqPrice() {
        return liqPrice;
    }

    public String getAvgOpenPrice() {
        return avgOpenPrice;
    }

    public String getMarginRate() {
        return marginRate;
    }

    public Long getCtime() {
        return ctime;
    }

    public Long getMtime() {
        return mtime;
    }
}
