package com.bitunix.openapi.request.ws;


import java.util.List;

public class DepthSubReq extends BasicSubEntity {

    private List<DepthSubArg> args;

    public DepthSubReq() {
    }

    public DepthSubReq(List<DepthSubArg> args) {
        this.args = args;
    }

    public List<DepthSubArg> getArgs() {
        return args;
    }

    public void setArgs(List<DepthSubArg> args) {
        this.args = args;
    }
}
