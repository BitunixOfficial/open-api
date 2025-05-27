package com.bitunix.openapi.request.ws;


import java.util.List;

public class PriceSubReq extends BasicSubEntity {

    private List<PriceSubArg> args;

    public PriceSubReq() {
    }

    public PriceSubReq(List<PriceSubArg> args) {
        this.args = args;
    }

    public List<PriceSubArg> getArgs() {
        return args;
    }

    public void setArgs(List<PriceSubArg> args) {
        this.args = args;
    }
}
