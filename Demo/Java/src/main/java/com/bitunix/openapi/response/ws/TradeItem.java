package com.bitunix.openapi.response.ws;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class TradeItem {

    @JsonProperty("t")
    private String time;
    @JsonProperty("p")
    private BigDecimal price;
    @JsonProperty("v")
    private BigDecimal volume;
    @JsonProperty("s")
    private String side;

    public String getTime() {
        return time;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public String getSide() {
        return side;
    }
}
