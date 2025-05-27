package com.bitunix.openapi.client;

import com.bitunix.openapi.constants.FuturesPath;
import com.bitunix.openapi.constants.ServerConfig;
import com.bitunix.openapi.constants.WsOpCh;
import com.bitunix.openapi.listener.BitunixPrivateWsResponseListener;
import com.bitunix.openapi.request.WsLoginRequest;
import com.bitunix.openapi.request.WsPing;
import com.bitunix.openapi.request.ws.BasicWsEntity;
import com.bitunix.openapi.request.ws.LoginArg;
import com.bitunix.openapi.response.ws.*;
import com.bitunix.openapi.utils.JsonUtils;
import com.bitunix.openapi.utils.SignUtils;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FuturesWsPrivateClient {

    private final String apiKey;
    private final String apiSecret;

    private final Logger log = Logger.getLogger(FuturesWsPrivateClient.class.getName());
    private OkHttpClient okHttpClient = null;

    public FuturesWsPrivateClient(String apiKey, String apiSecret) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.okHttpClient = new OkHttpClient.Builder()
                .callTimeout(Duration.ofSeconds(60L))
                .connectTimeout(Duration.ofSeconds(60L))
                .readTimeout(Duration.ofSeconds(60L))
                .build();
    }

    public FuturesWsPrivateClient(String apiKey, String apiSecret, OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            throw new NullPointerException("okHttpClient couldn't be null");
        }
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.okHttpClient = okHttpClient;
    }

    private WebSocket webSocket = null;

    private Request wsRequest = null;

    private WebSocketListener webSocketListener = null;

    private BitunixPrivateWsResponseListener bitunixPrivateWsResponseListener = null;

    private Boolean active = false;

    public void connect(BitunixPrivateWsResponseListener listener) {
        this.bitunixPrivateWsResponseListener = listener;

        this.webSocketListener = new WebSocketListener() {
            @Override
            public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                active = true;
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull String content) {
                try {
                    if (bitunixPrivateWsResponseListener == null) {
                        return;
                    }
                    String opOrCh = getOpOrCh(content);
                    if (opOrCh == null) {
                        return;
                    }

                    switch (opOrCh) {
                        case WsOpCh.CONNECT -> {
                            //login
                            login("defaultnonce");
                            bitunixPrivateWsResponseListener.onConnectSuccess(JsonUtils.readToObject(content, WsConnectSuc.class));
                        }
                        case WsOpCh.PING ->
                                bitunixPrivateWsResponseListener.onPong(JsonUtils.readToObject(content, WsPong.class));
                        case WsOpCh.ORDER ->
                                bitunixPrivateWsResponseListener.onOrderChange(JsonUtils.readToObject(content, OrderResp.class));
                        case WsOpCh.LOGIN -> {
                            log.info("login success :" + content);
                            bitunixPrivateWsResponseListener.afterLogin();
                        }
                        case WsOpCh.BALANCE ->
                                bitunixPrivateWsResponseListener.onBalanceChange(JsonUtils.readToObject(content, BalanceResp.class));
                        case WsOpCh.POSITION ->
                                bitunixPrivateWsResponseListener.onPositionChange(JsonUtils.readToObject(content, PositionResp.class));
                        case WsOpCh.TPSL ->
                                bitunixPrivateWsResponseListener.onTpslChange(JsonUtils.readToObject(content, TpslResp.class));
                        default -> log.warning("unknown ch: " + opOrCh + " msg:" + content);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                log.info("ws closed");
                active = false;
                reconnect();
            }

            @Override
            public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
                log.warning("ws failure");
                active = false;
                reconnect();
            }
        };
        Request.Builder request = new Request.Builder()
                .url(ServerConfig.WS_SCHEMA + "://" + ServerConfig.HOST + FuturesPath.WS_PRIVATE);
        wsRequest = request.build();

        this.webSocket = okHttpClient.newWebSocket(wsRequest, webSocketListener);
        active = true;
        //start auto ping
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            while (true) {
                try {
                    if (active) {
                        webSocket.send(JsonUtils.toJsonString(new WsPing(Instant.now().getEpochSecond())));
                    }
                    Thread.sleep(10000L);
                } catch (Exception e) {
                    log.log(Level.WARNING, e, () -> "ping message send fail");
                    break;
                }
            }
        });
    }

    public void reconnect() {
        log.warning("reconnect ...");
        this.webSocket = okHttpClient.newWebSocket(wsRequest, webSocketListener);
    }

    private void login(String nonce) {
        long now = System.currentTimeMillis();
        String timestamp = String.valueOf(now);
        String sign = SignUtils.generateSign(nonce, timestamp, apiKey, new TreeMap<>(), "", apiSecret);
        WsLoginRequest wsLoginRequest = new WsLoginRequest();
        wsLoginRequest.setArgs(Collections.singletonList(new LoginArg(apiKey, now, nonce, sign)));
        String json = JsonUtils.toJsonString(wsLoginRequest);
        log.info("login request :" + json);
        webSocket.send(json);
    }

    private String getOpOrCh(String content) {
        try {
            BasicWsEntity basicWsEntity = JsonUtils.readToObject(content, BasicWsEntity.class);
            if (basicWsEntity == null) {
                return null;
            }
            if (basicWsEntity.getOp() == null) {
                BasicWsRespEntity basicWsRespEntity = JsonUtils.readToObject(content, BasicWsRespEntity.class);
                return basicWsRespEntity.getCh();
            }

            return basicWsEntity.getOp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
