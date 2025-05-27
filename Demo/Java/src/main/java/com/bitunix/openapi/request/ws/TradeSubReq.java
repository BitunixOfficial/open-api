package com.bitunix.openapi.request.ws;


import java.util.List;

public class TradeSubReq extends BasicSubEntity {

    private List<TradeSubArg> args;

    public TradeSubReq() {
    }

    public TradeSubReq(List<TradeSubArg> args) {
        this.args = args;
    }

    public void setArgs(List<TradeSubArg> args) {
        this.args = args;
    }

    public List<TradeSubArg> getArgs() {
        return args;
    }
}
