package com.bitunix.openapi.response;

import java.math.BigDecimal;
import java.util.List;

public class Depth {

    private List<List<BigDecimal>> asks;
    private List<List<BigDecimal>> bids;

    public List<List<BigDecimal>> getAsks() {
        return asks;
    }

    public List<List<BigDecimal>> getBids() {
        return bids;
    }
}
