package com.bitunix.openapi.request;

import java.util.TreeMap;

public class GetHistoryTradesRequest extends PageRequest implements GetRequest{

    private String marginCoin;

    private String symbol;

    private String orderId;

    private String positionId;

    private Long startTime;

    private Long endTime;

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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    @Override
    public TreeMap<String, String> toTreeMap() {
        TreeMap<String,String> treeMap = new TreeMap<>();
        if (marginCoin != null){
            treeMap.put("marginCoin", marginCoin);
        }
        if (symbol != null){
            treeMap.put("symbol", symbol);
        }
        if (orderId != null){
            treeMap.put("orderId", orderId);
        }
        if (positionId != null){
            treeMap.put("positionId", positionId);
        }
        if (startTime != null){
            treeMap.put("startTime", startTime.toString());
        }
        if (endTime != null){
            treeMap.put("endTime", endTime.toString());
        }
        if (getLimit() != null){
            treeMap.put("limit", getLimit().toString());
        }
        if (getSkip() != null){
            treeMap.put("skip", getSkip().toString());
        }
        return treeMap;
    }
}
