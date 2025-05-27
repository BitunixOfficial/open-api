package com.bitunix.openapi.response;


import com.bitunix.openapi.enums.MarginMode;
import com.bitunix.openapi.enums.PositionMode;

import java.math.BigDecimal;

public class PositionHistoryResp {

    private String positionId;

    private String symbol;

    private String marginCoin;

    private BigDecimal maxQty;

    private BigDecimal qty;

    private BigDecimal entryPrice;

    private BigDecimal closePrice;

    private BigDecimal liqQty;

    private String side;

    private MarginMode marginMode;

    private PositionMode positionMode;

    private Long leverage;

    private BigDecimal fee;

    private BigDecimal funding;

    private BigDecimal realizedPNL;

    private BigDecimal margin;


    private BigDecimal liqPrice;


    private Long ctime;

    private Long mtime;

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

    public String getMarginCoin() {
        return marginCoin;
    }

    public void setMarginCoin(String marginCoin) {
        this.marginCoin = marginCoin;
    }

    public BigDecimal getMaxQty() {
        return maxQty;
    }

    public void setMaxQty(BigDecimal maxQty) {
        this.maxQty = maxQty;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getEntryPrice() {
        return entryPrice;
    }

    public void setEntryPrice(BigDecimal entryPrice) {
        this.entryPrice = entryPrice;
    }

    public BigDecimal getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(BigDecimal closePrice) {
        this.closePrice = closePrice;
    }

    public BigDecimal getLiqQty() {
        return liqQty;
    }

    public void setLiqQty(BigDecimal liqQty) {
        this.liqQty = liqQty;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public MarginMode getMarginMode() {
        return marginMode;
    }

    public void setMarginMode(MarginMode marginMode) {
        this.marginMode = marginMode;
    }

    public PositionMode getPositionMode() {
        return positionMode;
    }

    public void setPositionMode(PositionMode positionMode) {
        this.positionMode = positionMode;
    }

    public Long getLeverage() {
        return leverage;
    }

    public void setLeverage(Long leverage) {
        this.leverage = leverage;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getFunding() {
        return funding;
    }

    public void setFunding(BigDecimal funding) {
        this.funding = funding;
    }

    public BigDecimal getRealizedPNL() {
        return realizedPNL;
    }

    public void setRealizedPNL(BigDecimal realizedPNL) {
        this.realizedPNL = realizedPNL;
    }

    public BigDecimal getMargin() {
        return margin;
    }

    public void setMargin(BigDecimal margin) {
        this.margin = margin;
    }

    public BigDecimal getLiqPrice() {
        return liqPrice;
    }

    public void setLiqPrice(BigDecimal liqPrice) {
        this.liqPrice = liqPrice;
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
