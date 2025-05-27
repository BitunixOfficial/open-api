package com.bitunix.openapi.test;

import com.bitunix.openapi.client.FuturesPrivateApiClient;
import com.bitunix.openapi.client.FuturesWsPrivateClient;
import com.bitunix.openapi.enums.Language;
import com.bitunix.openapi.enums.OrderType;
import com.bitunix.openapi.listener.BitunixPrivateWsResponseListener;
import com.bitunix.openapi.request.CancelOrdersRequest;
import com.bitunix.openapi.request.OrderIdRequest;
import com.bitunix.openapi.request.PlaceOrderRequest;
import com.bitunix.openapi.response.Account;
import com.bitunix.openapi.response.OrderIdResp;
import com.bitunix.openapi.response.OrderResult;
import com.bitunix.openapi.response.ws.*;
import com.bitunix.openapi.utils.JsonUtils;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;

public class StageTest {

    @Test
    public void test() throws InterruptedException {
        String apiKey = "1457495895d39196b7cea149bf6cec79";
        String apiSecret = "f58162e28c6479e9c3f0f7354ae21c35";
        String symbol = "BTCUSDT";
        // start private websocket
        FuturesWsPrivateClient futuresWsPrivateClient = new FuturesWsPrivateClient(apiKey,apiSecret);
        futuresWsPrivateClient.connect(new BitunixPrivateWsResponseListener() {
            @Override
            public void onPong(WsPong wsPong) {
                System.out.println("pong "+wsPong.getPong());
            }

            @Override
            public void afterLogin() {
            }

            @Override
            public void onOrderChange(OrderResp orderResp) {
                System.out.println("[ws]order change :"+orderResp.getData().getEvent()+ "  data: "+ JsonUtils.toJsonString(orderResp));
            }

            @Override
            public void onBalanceChange(BalanceResp balanceResp) {
                System.out.println("[ws]balance change data: "+ JsonUtils.toJsonString(balanceResp));
            }

            @Override
            public void onPositionChange(PositionResp positionResp) {
                System.out.println("[ws]position change data: "+ JsonUtils.toJsonString(positionResp));
            }

            @Override
            public void onTpslChange(TpslResp tpslResp) {
                System.out.println("[ws]tpsl change data: "+ JsonUtils.toJsonString(tpslResp));
            }
        });
        FuturesPrivateApiClient futuresPrivateApiClient = new FuturesPrivateApiClient(apiKey,apiSecret, Language.English);
        // query user's account
        Account account = futuresPrivateApiClient.getAccount("USDT");
        System.out.println("user's account balance: "+account.getAvailable());
        //place a limit order
        PlaceOrderRequest placeOrderRequest = new PlaceOrderRequest();
        placeOrderRequest.setQty(new BigDecimal("0.01"));
        placeOrderRequest.setSymbol(symbol);
        placeOrderRequest.setPrice(new BigDecimal("80000"));
        placeOrderRequest.setSide("BUY");
        placeOrderRequest.setOrderType(OrderType.LIMIT);
        OrderIdResp orderIdResp = futuresPrivateApiClient.placeOrder(placeOrderRequest);
        //after place order ,websocket will receive a msg that event = 'CREATE'
        System.out.println("place order result "+JsonUtils.toJsonString(orderIdResp));
        //cancel this order
        String orderId = orderIdResp.getOrderId();
        CancelOrdersRequest cancelOrdersRequest = new CancelOrdersRequest();
        OrderIdRequest orderIdRequest = new OrderIdRequest();
        orderIdRequest.setOrderId(orderId);
        cancelOrdersRequest.setOrderList(Collections.singletonList(orderIdRequest));
        OrderResult orderResult = futuresPrivateApiClient.cancelOrders(cancelOrdersRequest);
        System.out.println("cancel order result: "+JsonUtils.toJsonString(orderResult));
        //after place order ,websocket will receive a msg that event = 'CLOSE'

        //sleep for ws
        Thread.sleep(1000000);
    }
}
