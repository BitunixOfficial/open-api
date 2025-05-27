package com.bitunix.openapi.request;

import java.util.TreeMap;

public class GetPendingPositionRequest implements GetRequest{

    private String symbol;

    private String positionId;


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

    @Override
    public TreeMap<String, String> toTreeMap() {
        TreeMap<String,String> treeMap = new TreeMap<>();
        if (positionId != null){
            treeMap.put("positionId", positionId);
        }
        if (symbol != null){
            treeMap.put("symbol", symbol);
        }
        return treeMap;
    }
}
