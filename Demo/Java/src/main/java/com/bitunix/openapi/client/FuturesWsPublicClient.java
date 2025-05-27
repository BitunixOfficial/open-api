package com.bitunix.openapi.client;

import com.bitunix.openapi.constants.FuturesPath;
import com.bitunix.openapi.constants.ServerConfig;
import com.bitunix.openapi.constants.WsOpCh;
import com.bitunix.openapi.listener.BitunixPublicWsResponseListener;
import com.bitunix.openapi.request.ws.*;
import com.bitunix.openapi.request.WsPing;
import com.bitunix.openapi.response.ws.*;
import com.bitunix.openapi.utils.JsonUtils;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FuturesWsPublicClient {

    private final Logger log = Logger.getLogger(FuturesWsPublicClient.class.getName());
    private OkHttpClient okHttpClient = null;

    public FuturesWsPublicClient() {
        this.okHttpClient = new OkHttpClient.Builder()
                .callTimeout(Duration.ofSeconds(60L))
                .connectTimeout(Duration.ofSeconds(60L))
                .readTimeout(Duration.ofSeconds(60L))
                .build();
    }

    public FuturesWsPublicClient(OkHttpClient okHttpClient) {
        if (okHttpClient == null){
            throw new NullPointerException("okHttpClient couldn't be null");
        }
        this.okHttpClient = okHttpClient;
    }

    private WebSocket webSocket = null;

    private BitunixPublicWsResponseListener bitunixPublicWsResponseListener = null;

    public void connect(BitunixPublicWsResponseListener listener){
        this.bitunixPublicWsResponseListener = listener;

        WebSocketListener webSocketListener = new WebSocketListener() {
            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull String content) {
                try {
                    if (bitunixPublicWsResponseListener == null) {
                        return;
                    }
                    String opOrCh = getOpOrCh(content);
                    if (opOrCh == null) {
                        return;
                    }
                    switch (opOrCh) {
                        case WsOpCh.CONNECT:
                            bitunixPublicWsResponseListener.onConnectSuccess(JsonUtils.readToObject(content, WsConnectSuc.class));
                            break;
                        case WsOpCh.PING:
                            bitunixPublicWsResponseListener.onPong(JsonUtils.readToObject(content, WsPong.class));
                            break;
                        case WsOpCh.TICKER:
                            bitunixPublicWsResponseListener.onTicker(JsonUtils.readToObject(content, TickerResp.class));
                            break;
                        case WsOpCh.TICKERS:
                            bitunixPublicWsResponseListener.onTickers(JsonUtils.readToObject(content, TickersResp.class));
                            break;
                        case WsOpCh.PRICE:
                            bitunixPublicWsResponseListener.onPrice(JsonUtils.readToObject(content, PriceResp.class));
                            break;
                        case WsOpCh.TRADE:
                            bitunixPublicWsResponseListener.onTrade(JsonUtils.readToObject(content, TradeResp.class));
                            break;
                        default:
                            if (opOrCh.contains("kline")){
                                bitunixPublicWsResponseListener.onKline(JsonUtils.readToObject(content, KlineResp.class));
                            }else if (opOrCh.contains("depth")){
                                bitunixPublicWsResponseListener.onDepth(JsonUtils.readToObject(content, DepthResp.class));
                            }else {
                                log.warning("unknown ch: " + opOrCh + " msg:" + content);
                            }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Request.Builder request = new Request.Builder()
                .url(ServerConfig.WS_SCHEMA + "://" + ServerConfig.HOST + FuturesPath.WS_PUBLIC);
        this.webSocket = okHttpClient.newWebSocket(request.build(), webSocketListener);
        //start auto heartbeat
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(()->{
            while (true){
                try {
                    webSocket.send(JsonUtils.toJsonString(new WsPing(Instant.now().getEpochSecond())));
                    Thread.sleep(10000L);
                }catch (Exception e){
                    log.log(Level.WARNING,e,()->"ping message send fail");
                    break;
                }
            }
        });
    }

    private void subscribe(BasicSubEntity basicSubEntity){
        if (webSocket == null){
            return;
        }
        String json = JsonUtils.toJsonString(basicSubEntity);
        log.info("subscribe :"+json);
        webSocket.send(json);
    }

    public void subKline(KlineSubReq klineSubReq){
        subscribe(klineSubReq);
    }
    public void subDepth(DepthSubReq depthSubReq){
        subscribe(depthSubReq);
    }
    public void subTicker(TickerSubReq tickerSubReq){
        subscribe(tickerSubReq);
    }
    public void subTickers(TickersSubReq tickersSubReq){
        subscribe(tickersSubReq);
    }
    public void subPrice(PriceSubReq priceSubReq){
        subscribe(priceSubReq);
    }
    public void subTrade(TradeSubReq tradeSubReq){
        subscribe(tradeSubReq);
    }

    public void unsubscribe(BasicUnsubEntity basicUnsubEntity){
        if (webSocket == null){
            return;
        }
        String json = JsonUtils.toJsonString(basicUnsubEntity);
        log.info("unsubscribe :"+json);
        webSocket.send(json);
    }


    private String getOpOrCh(String content){
        try {
            BasicWsEntity basicWsEntity = JsonUtils.readToObject(content, BasicWsEntity.class);
            if (basicWsEntity == null){
                return null;
            }
            if (basicWsEntity.getOp() == null){
                BasicWsRespEntity basicWsRespEntity = JsonUtils.readToObject(content, BasicWsRespEntity.class);
                return basicWsRespEntity.getCh();
            }

            return basicWsEntity.getOp();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
