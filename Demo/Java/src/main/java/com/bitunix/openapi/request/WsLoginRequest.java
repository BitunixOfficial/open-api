package com.bitunix.openapi.request;

import com.bitunix.openapi.request.ws.BasicWsEntity;
import com.bitunix.openapi.request.ws.LoginArg;

import java.util.List;

public class WsLoginRequest extends BasicWsEntity {

    private List<LoginArg> args;

    public WsLoginRequest() {
        super("login");
    }

    public List<LoginArg> getArgs() {
        return args;
    }

    public void setArgs(List<LoginArg> args) {
        this.args = args;
    }
}
