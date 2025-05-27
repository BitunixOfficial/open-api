package com.bitunix.openapi.request.ws;


import java.util.List;

public class KlineSubReq extends BasicSubEntity {

    private List<KlineSubArg> args;

    public KlineSubReq() {
    }

    public KlineSubReq(List<KlineSubArg> args) {
        this.args = args;
    }

    public List<KlineSubArg> getArgs() {
        return args;
    }

    public void setArgs(List<KlineSubArg> args) {
        this.args = args;
    }
}
