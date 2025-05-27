package com.bitunix.openapi.response;

import java.util.List;

public class TradePageResp extends PageResp {

    private List<TradeResp> tradeList;

    public List<TradeResp> getTradeList() {
        return tradeList;
    }
}
