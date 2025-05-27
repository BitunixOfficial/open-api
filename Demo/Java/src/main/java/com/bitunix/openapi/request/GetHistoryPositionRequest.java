package com.bitunix.openapi.request;


import java.util.TreeMap;

public class GetHistoryPositionRequest extends PageRequest implements GetRequest{

    private String symbol;

    private String positionId;

    private Long startTime;

    private Long endTime;

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
        if (positionId != null){
            treeMap.put("positionId", positionId);
        }
        if (symbol != null){
            treeMap.put("symbol", symbol);
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
