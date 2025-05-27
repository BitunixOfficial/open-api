package com.bitunix.openapi.request.ws;


public class BasicWsEntity {

    private String op;

    public BasicWsEntity() {
    }

    public BasicWsEntity(String op) {
        this.op = op;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }
}
