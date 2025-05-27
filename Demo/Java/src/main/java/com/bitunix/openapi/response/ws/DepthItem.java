package com.bitunix.openapi.response.ws;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public class DepthItem {
    @JsonProperty("b")
    private List<List<BigDecimal>> bids;
    @JsonProperty("a")
    private List<List<BigDecimal>> asks;
}
