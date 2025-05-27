package com.bitunix.openapi.response;

import java.util.List;

public class OrderPageResp extends PageResp {

    private List<OrderResp> orderList;

    public List<OrderResp> getOrderList() {
        return orderList;
    }
}
