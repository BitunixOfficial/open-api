package com.bitunix.openapi.listener;

import com.bitunix.openapi.response.ws.*;

public abstract class BitunixPrivateWsResponseListener implements BitunixWsResponseListener{

    public void onConnectSuccess(WsConnectSuc wsConnectSuc){

    }

    public void onPong(WsPong wsPong){

    }

    public void afterLogin(){

    }

    public void onOrderChange(OrderResp orderResp) {

    }

    public void onBalanceChange(BalanceResp balanceResp) {

    }

    public void onPositionChange(PositionResp positionResp) {

    }

    public void onTpslChange(TpslResp tpslResp) {

    }
}
