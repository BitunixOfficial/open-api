package com.bitunix.openapi.request;

import java.util.TreeMap;

public class GetPositionTiersRequest implements GetRequest{

    private String symbol;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public TreeMap<String, String> toTreeMap() {
        TreeMap<String,String> treeMap = new TreeMap<>();
        if (symbol != null){
            treeMap.put("symbol", symbol);
        }
        return treeMap;
    }
}
