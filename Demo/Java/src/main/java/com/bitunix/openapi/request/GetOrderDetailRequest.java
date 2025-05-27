package com.bitunix.openapi.request;

import java.util.TreeMap;

public class GetOrderDetailRequest implements GetRequest{

    private String orderId;

    private String clientId;

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

    @Override
    public TreeMap<String, String> toTreeMap() {
        TreeMap<String, String> treeMap = new TreeMap<>();
        if (orderId != null){
            treeMap.put("orderId", orderId);
        }
        if (clientId != null){
            treeMap.put("clientId", clientId);
        }
        return treeMap;
    }
}
