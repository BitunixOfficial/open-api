package com.bitunix.openapi.request.ws;


import java.util.List;

public class TickerSubReq extends BasicSubEntity {

    private List<TickerSubArg> args;

    public TickerSubReq() {
    }

    public TickerSubReq(List<TickerSubArg> args) {
        this.args = args;
    }

    public List<TickerSubArg> getArgs() {
        return args;
    }

    public void setArgs(List<TickerSubArg> args) {
        this.args = args;
    }
}
