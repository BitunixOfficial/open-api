package com.bitunix.openapi.request;

import com.bitunix.openapi.request.ws.BasicWsEntity;

public class WsPing extends BasicWsEntity {

    private Long ping;

    public WsPing(Long pingSeconds) {
        super("ping");
        this.ping = pingSeconds;
    }

    public Long getPing() {
        return ping;
    }
}
