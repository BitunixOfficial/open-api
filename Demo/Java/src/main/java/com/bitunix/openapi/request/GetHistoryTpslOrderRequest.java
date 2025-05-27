package com.bitunix.openapi.request;


import java.util.TreeMap;

public class GetHistoryTpslOrderRequest extends PageRequest implements GetRequest{

    private String symbol;

    private String positionId;

    private Integer side;

    private Integer positionMode;

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

    public Integer getSide() {
        return side;
    }

    public void setSide(Integer side) {
        this.side = side;
    }

    public Integer getPositionMode() {
        return positionMode;
    }

    public void setPositionMode(Integer positionMode) {
        this.positionMode = positionMode;
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

        if (symbol != null){
            treeMap.put("symbol", symbol);
        }
        if (positionId != null){
            treeMap.put("positionId", positionId);
        }
        if (side != null){
            treeMap.put("side", side.toString());
        }
        if (positionMode != null){
            treeMap.put("positionMode", positionMode.toString());
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
