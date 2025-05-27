package com.bitunix.openapi.response.ws;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class KlineItem {
    @JsonProperty("o")
    private BigDecimal open;
    @JsonProperty("h")
    private BigDecimal high;
    @JsonProperty("l")
    private BigDecimal low;
    @JsonProperty("c")
    private BigDecimal close;
    @JsonProperty("b")
    private BigDecimal baseVolume;
    @JsonProperty("q")
    private BigDecimal quoteVolume;
}
