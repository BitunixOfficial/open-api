package com.bitunix.openapi.test;

import com.bitunix.openapi.client.FuturesWsPublicClient;
import com.bitunix.openapi.enums.DepthLevel;
import com.bitunix.openapi.enums.KlineInterval;
import com.bitunix.openapi.enums.KlineType;
import com.bitunix.openapi.listener.BitunixPublicWsResponseListener;
import com.bitunix.openapi.request.ws.*;
import com.bitunix.openapi.response.Kline;
import com.bitunix.openapi.response.ws.*;
import com.bitunix.openapi.utils.JsonUtils;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class PublicWsTest {

    @Test
    public void test() throws InterruptedException {

        FuturesWsPublicClient futuresWsPublicClient = new FuturesWsPublicClient();
        futuresWsPublicClient.connect(new BitunixPublicWsResponseListener() {
            @Override
            public void onConnectSuccess(WsConnectSuc wsConnectSuc) {
                System.out.println("connect success");
            }

            @Override
            public void onPong(WsPong wsPong) {
                System.out.println("pong : "+wsPong.getPong());
            }

            @Override
            public void onTicker(TickerResp tickerResp) {
                System.out.println("ticker : "+ JsonUtils.toJsonString(tickerResp));
            }

            @Override
            public void onTickers(TickersResp tickersResp) {
                System.out.println("tickers : "+JsonUtils.toJsonString(tickersResp));
            }

            @Override
            public void onPrice(PriceResp priceResp) {
                System.out.println("price : "+JsonUtils.toJsonString(priceResp));
            }

            @Override
            public void onTrade(TradeResp tradeResp) {
                System.out.println("trade : "+JsonUtils.toJsonString(tradeResp));
            }

            @Override
            public void onKline(KlineResp klineResp) {
                System.out.println("kline : "+JsonUtils.toJsonString(klineResp));
            }

            @Override
            public void onDepth(DepthResp depthResp) {
                System.out.println("depth : "+JsonUtils.toJsonString(depthResp));
            }
        });
        //sub ticker of BTCUSDT
        futuresWsPublicClient.subTicker(new TickerSubReq(Collections.singletonList(new TickerSubArg("BTCUSDT"))));
        futuresWsPublicClient.subTickers(new TickersSubReq(Collections.singletonList(new TickersSubArg("BTCUSDT"))));
        futuresWsPublicClient.subPrice(new PriceSubReq(Collections.singletonList(new PriceSubArg("BTCUSDT"))));
        futuresWsPublicClient.subKline(new KlineSubReq(Collections.singletonList(new KlineSubArg(KlineType.MARK_PRICE, KlineInterval.MINUTE_1,"BTCUSDT"))));
        futuresWsPublicClient.subDepth(new DepthSubReq(Collections.singletonList(new DepthSubArg(DepthLevel.ONE,"BTCUSDT"))));
        futuresWsPublicClient.subTrade(new TradeSubReq(Collections.singletonList(new TradeSubArg("BTCUSDT"))));
        Thread.sleep(1000000L);

    }
}
