package com.bitunix.openapi.request;

import com.bitunix.openapi.enums.OrderStatus;

import java.util.TreeMap;

public class GetPendingOrdersRequest extends PageRequest implements GetRequest{

    private String marginCoin;

    private String symbol;

    private String orderId;

    private String clientId;

    private OrderStatus status;

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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
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
        if (clientId != null){
            treeMap.put("clientId", clientId);
        }
        if (status != null){
            treeMap.put("status", status.name());
        }
        if (startTime != null){
            treeMap.put("startTime", startTime.toString());
        }
        if (endTime != null){
            treeMap.put("endTime", endTime.toString());
        }
        if (getSkip() != null){
            treeMap.put("skip", getSkip().toString());
        }
        if (getLimit() != null){
            treeMap.put("limit", getLimit().toString());
        }

        return treeMap;
    }
}
