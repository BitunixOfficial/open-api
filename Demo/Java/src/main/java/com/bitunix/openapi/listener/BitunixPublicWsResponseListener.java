package com.bitunix.openapi.listener;

import com.bitunix.openapi.response.ws.*;

public abstract class BitunixPublicWsResponseListener implements BitunixWsResponseListener{

    public void onConnectSuccess(WsConnectSuc wsConnectSuc){

    }

    public void onPong(WsPong wsPong){

    }

    public void onTicker(TickerResp tickerResp){

    }
    public void onTickers(TickersResp tickersResp){

    }
    public void onPrice(PriceResp priceResp){

    }

    public void onTrade(TradeResp tradeResp){

    }
    public void onKline(KlineResp klineResp){

    }

    public void onDepth(DepthResp depthResp){

    }
}
