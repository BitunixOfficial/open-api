package com.bitunix.openapi.test;

import com.bitunix.openapi.client.FuturesWsPrivateClient;
import com.bitunix.openapi.listener.BitunixPrivateWsResponseListener;
import com.bitunix.openapi.response.ws.*;
import com.bitunix.openapi.utils.JsonUtils;
import org.junit.jupiter.api.Test;

public class PrivateWsTest {

    @Test
    public void test(){
        // private websocket will auto subscribe all user's data when connected success
        FuturesWsPrivateClient futuresWsPrivateClient = new FuturesWsPrivateClient("1457495895d39196b7cea149bf6cec79","f58162e28c6479e9c3f0f7354ae21c35");
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
                System.out.println("order change :"+orderResp.getData().getEvent()+ "  data: "+JsonUtils.toJsonString(orderResp));
            }

            @Override
            public void onBalanceChange(BalanceResp balanceResp) {
                System.out.println("balance change data: "+ JsonUtils.toJsonString(balanceResp));
            }

            @Override
            public void onPositionChange(PositionResp positionResp) {
                System.out.println("position change data: "+ JsonUtils.toJsonString(positionResp));
            }

            @Override
            public void onTpslChange(TpslResp tpslResp) {
                System.out.println("tpsl change data: "+ JsonUtils.toJsonString(tpslResp));
            }
        });

        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
