package com.bitunix.openapi.test;

import com.bitunix.openapi.client.FuturesPrivateApiClient;
import com.bitunix.openapi.enums.Language;
import com.bitunix.openapi.enums.OrderType;
import com.bitunix.openapi.request.PlaceOrderRequest;
import com.bitunix.openapi.response.OrderIdResp;
import com.bitunix.openapi.utils.JsonUtils;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class PlaceOrderTest {

    @Test
    public void test(){
        FuturesPrivateApiClient futuresPrivateApiClient = new FuturesPrivateApiClient("1457495895d39196b7cea149bf6cec79","f58162e28c6479e9c3f0f7354ae21c35", Language.French);

        PlaceOrderRequest placeOrderRequest = new PlaceOrderRequest();
        placeOrderRequest.setQty(new BigDecimal("0.01"));
        placeOrderRequest.setSymbol("BTCUSDT");
        placeOrderRequest.setPrice(new BigDecimal("80000"));
        placeOrderRequest.setSide("BUY");
        placeOrderRequest.setOrderType(OrderType.LIMIT);
        OrderIdResp orderIdResp = futuresPrivateApiClient.placeOrder(placeOrderRequest);
        System.out.println(JsonUtils.toJsonString(orderIdResp));
    }
}
