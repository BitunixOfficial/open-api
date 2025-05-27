package com.bitunix.openapi.request.ws;


import java.util.List;

public class TickersSubReq extends BasicSubEntity {

    private List<TickersSubArg> args;

    public TickersSubReq() {
    }

    public TickersSubReq(List<TickersSubArg> args) {
        this.args = args;
    }

    public List<TickersSubArg> getArgs() {
        return args;
    }

    public void setArgs(List<TickersSubArg> args) {
        this.args = args;
    }
}
