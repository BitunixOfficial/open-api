package com.bitunix.openapi.listener;

import com.bitunix.openapi.response.ws.OrderResp;
import com.bitunix.openapi.response.ws.WsConnectSuc;
import com.bitunix.openapi.response.ws.WsPong;

public interface BitunixWsResponseListener {

    void onConnectSuccess(WsConnectSuc wsConnectSuc);

    void onPong(WsPong wsPong);




}
