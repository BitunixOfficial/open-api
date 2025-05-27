package com.bitunix.openapi.response;

import java.util.List;

public class TpslHistoryOrdersPageResp extends PageResp {

    private List<TpslHistoryOrderResp> orderList;

    public List<TpslHistoryOrderResp> getOrderList() {
        return orderList;
    }
}
