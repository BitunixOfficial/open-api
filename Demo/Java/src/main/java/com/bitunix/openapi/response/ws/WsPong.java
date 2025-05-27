package com.bitunix.openapi.response.ws;

import com.bitunix.openapi.request.ws.BasicWsEntity;

public class WsPong extends BasicWsEntity {

    private Long ping;
    private Long pong;

    public WsPong() {
        super("ping");
    }

    public Long getPing() {
        return ping;
    }

    public void setPing(Long ping) {
        this.ping = ping;
    }

    public Long getPong() {
        return pong;
    }

    public void setPong(Long pong) {
        this.pong = pong;
    }
}
