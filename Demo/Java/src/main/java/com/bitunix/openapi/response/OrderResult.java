package com.bitunix.openapi.response;


import java.util.List;

public class OrderResult {
    private List<OrderIdResp> successList;

    private List<OrderFailResult> failureList;

    public List<OrderIdResp> getSuccessList() {
        return successList;
    }

    public List<OrderFailResult> getFailureList() {
        return failureList;
    }
}
