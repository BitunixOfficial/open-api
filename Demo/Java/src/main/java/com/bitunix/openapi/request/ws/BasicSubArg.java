package com.bitunix.openapi.request.ws;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BasicSubArg {

    @JsonProperty("ch")
    private String channel;

    public BasicSubArg() {
    }

    public BasicSubArg(String channel) {
        this.channel = channel;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
