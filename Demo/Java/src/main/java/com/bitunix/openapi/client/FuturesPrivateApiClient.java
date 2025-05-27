package com.bitunix.openapi.client;

import com.bitunix.openapi.constants.FuturesPath;
import com.bitunix.openapi.enums.Language;
import com.bitunix.openapi.constants.ServerConfig;
import com.bitunix.openapi.request.*;
import com.bitunix.openapi.response.*;
import com.bitunix.openapi.utils.HttpUtils;
import com.bitunix.openapi.utils.JsonUtils;
import com.bitunix.openapi.utils.SignUtils;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FuturesPrivateApiClient {
    private final String apiKey;
    private final String apiSecret;

    private String nonce = "";

    private Language language;

    private HttpUtils httpUtils;
    private OkHttpClient defaultOkHttpClient = new OkHttpClient.Builder()
            .callTimeout(Duration.ofSeconds(60L))
            .connectTimeout(Duration.ofSeconds(60L))
            .readTimeout(Duration.ofSeconds(60L))
            .build();

    public FuturesPrivateApiClient(String apiKey, String apiSecret, Language language){
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.httpUtils = new HttpUtils(defaultOkHttpClient);
        this.language = language;
    }

    public Account getAccount(String marginCoin){
        HttpUrl.Builder builder = new HttpUrl.Builder()
                .scheme(ServerConfig.HTTPS_SCHEMA)
                .host(ServerConfig.HOST)
                .port(ServerConfig.port)
                .addPathSegment(FuturesPath.GET_ACCOUNT)
                .addQueryParameter("marginCoin", marginCoin);
        return httpUtils.getForObject(builder.build(), getHeaders(nonce,null, getTreeMap("marginCoin",marginCoin)), Account.class);
    }

    private <T> T simplePost(Object requestBody,String path,Class<T> respCls){
        HttpUrl.Builder builder = new HttpUrl.Builder()
                .scheme(ServerConfig.HTTPS_SCHEMA)
                .host(ServerConfig.HOST)
                .port(ServerConfig.port)
                .addPathSegment(path);

        String body = JsonUtils.toJsonString(requestBody);
        return httpUtils.postForObject(builder.build(), getHeaders(nonce, body,null),body, respCls);
    }
    private <T> List<T> simplePostList(Object requestBody,String path,Class<T> respCls){
        HttpUrl.Builder builder = new HttpUrl.Builder()
                .scheme(ServerConfig.HTTPS_SCHEMA)
                .host(ServerConfig.HOST)
                .port(ServerConfig.port)
                .addPathSegment(path);

        String body = JsonUtils.toJsonString(requestBody);
        return httpUtils.postForList(builder.build(), getHeaders(nonce, body,null),body, respCls);
    }

    private <T> T simpleGet(GetRequest request,String path,Class<T> respCls){
        HttpUrl.Builder builder = new HttpUrl.Builder()
                .scheme(ServerConfig.HTTPS_SCHEMA)
                .host(ServerConfig.HOST)
                .port(ServerConfig.port)
                .addPathSegment(path);

        TreeMap<String, String> treeMap = request.toTreeMap();
        for (Map.Entry<String, String> entry : treeMap.entrySet()) {
            builder.addQueryParameter(entry.getKey(),entry.getValue());
        }

        return httpUtils.postForObject(builder.build(), getHeaders(nonce, null,treeMap),null, respCls);
    }

    private <T> ArrayList<T> simpleGetList(GetRequest request, String path, Class<T> respCls){
        HttpUrl.Builder builder = new HttpUrl.Builder()
                .scheme(ServerConfig.HTTPS_SCHEMA)
                .host(ServerConfig.HOST)
                .port(ServerConfig.port)
                .addPathSegment(path);

        TreeMap<String, String> treeMap = request.toTreeMap();
        for (Map.Entry<String, String> entry : treeMap.entrySet()) {
            builder.addQueryParameter(entry.getKey(),entry.getValue());
        }

        return httpUtils.postForList(builder.build(), getHeaders(nonce, null,treeMap),null, respCls);
    }

    public MarketSetting getLeverageAndMarginMode(String symbol, String marginCoin){
        HttpUrl.Builder builder = new HttpUrl.Builder()
                .scheme(ServerConfig.HTTPS_SCHEMA)
                .host(ServerConfig.HOST)
                .port(ServerConfig.port)
                .addPathSegment(FuturesPath.GET_LEVERAGE_AND_MARGIN_MODE)
                .addQueryParameter("marginCoin", marginCoin)
                .addQueryParameter("symbol", symbol);
        return httpUtils.getForObject(builder.build(), getHeaders(nonce,null, getTreeMap("marginCoin",marginCoin,"symbol",symbol)), MarketSetting.class);
    }

    public ChangePositionMode changePositionMode(ChangePositionMode changePositionMode){
        return simplePost(changePositionMode,FuturesPath.CHANGE_POSITION_MODE,ChangePositionMode.class);
    }

    public ChangeMarginMode changeMarginMode(ChangeMarginMode changeMarginMode){
        return simplePost(changeMarginMode,FuturesPath.CHANGE_MARGIN_MODE,ChangeMarginMode.class);
    }

    public ChangeLeverage changeLeverage(ChangeLeverage changeLeverage){
        return simplePost(changeLeverage,FuturesPath.CHANGE_LEVERAGE,ChangeLeverage.class);
    }

    public void adjustPositionMargin(AdjustPositionMarginRequest adjustPositionMarginRequest){
        simplePost(adjustPositionMarginRequest,FuturesPath.ADJUST_POSITION_MARGIN,String.class);
    }


    public OrderIdResp placeOrder(PlaceOrderRequest placeOrderRequest){
        return simplePost(placeOrderRequest,FuturesPath.PLACE_ORDER,OrderIdResp.class);
    }

    public OrderResult batchPlaceOrder(BatchPlaceOrderRequest batchPlaceOrderRequest){
        return simplePost(batchPlaceOrderRequest,FuturesPath.BATCH_PLACE_ORDER,OrderResult.class);
    }

    public OrderResult cancelOrders(CancelOrdersRequest cancelOrdersRequest){
        return simplePost(cancelOrdersRequest,FuturesPath.CANCEL_ORDERS,OrderResult.class);
    }

    public OrderResult cancelAllOrders(CancelAllOrdersRequest cancelOrdersRequest){
        return simplePost(cancelOrdersRequest,FuturesPath.CANCEL_ALL_ORDERS,OrderResult.class);
    }

    public void closeAllPosition(CloseAllPositionRequest closeAllPositionRequest){
        simplePost(closeAllPositionRequest,FuturesPath.CLOSE_ALL_POSITION,String.class);
    }
    public void flashClosePosition(FlashClosePositionRequest flashClosePositionRequest){
        simplePost(flashClosePositionRequest,FuturesPath.FLASH_CLOSE_POSITION,String.class);
    }

    public OrderPageResp getHistoryOrders(GetHistoryOrdersRequest getHistoryOrdersRequest){
        return simpleGet(getHistoryOrdersRequest,FuturesPath.GET_HISTORY_ORDERS, OrderPageResp.class);
    }

    public TradePageResp getHistoryTrades(GetHistoryTradesRequest getHistoryTradesRequest){
        return simpleGet(getHistoryTradesRequest,FuturesPath.GET_HISTORY_TRADES, TradePageResp.class);
    }

    public OrderResp getOrderDetail(GetOrderDetailRequest getOrderDetailRequest){
        return simpleGet(getOrderDetailRequest,FuturesPath.GET_ORDER_DETAIL, OrderResp.class);
    }

    public OrderPageResp getPendingOrders(GetPendingOrdersRequest getPendingOrdersRequest){
        return simpleGet(getPendingOrdersRequest,FuturesPath.GET_PENDING_ORDERS, OrderPageResp.class);
    }

    public OrderIdResp modifyOrder(ModifyOrderRequest modifyOrderRequest){
        return simplePost(modifyOrderRequest, FuturesPath.MODIFY_ORDER, OrderIdResp.class);
    }

    public PositionHistoryPageResp getHistoryPositions(GetHistoryPositionRequest getHistoryPositionRequest){
        return simpleGet(getHistoryPositionRequest,FuturesPath.GET_HISTORY_POSITIONS, PositionHistoryPageResp.class);
    }

    public ArrayList<PositionPendingResp> getPendingPositions(GetPendingPositionRequest getPendingPositionRequest){
        return simpleGetList(getPendingPositionRequest,FuturesPath.GET_PENDING_POSITIONS, PositionPendingResp.class);
    }

    public ArrayList<PositionTiersResp> getPositionTiers(GetPositionTiersRequest getPositionTiersRequest){
        return simpleGetList(getPositionTiersRequest,FuturesPath.GET_POSITION_TIERS, PositionTiersResp.class);
    }

    public OrderIdResp cancelTpslOrders(CancelTpslOrderRequest cancelTpslOrderRequest){
        return simplePost(cancelTpslOrderRequest,FuturesPath.CANCEL_TPSL_ORDERS, OrderIdResp.class);
    }

    public List<OrderIdResp> placeTpslOrders(PlaceTpslOrderRequest placeTpslOrderRequest){
        return simplePostList(placeTpslOrderRequest,FuturesPath.PLACE_TPSL_ORDER, OrderIdResp.class);
    }

    public TpslHistoryOrdersPageResp getHistoryTpslOrders(GetHistoryTpslOrderRequest getHistoryTpslOrderRequest){
        return simpleGet(getHistoryTpslOrderRequest,FuturesPath.GET_HISTORY_TPSL_ORDERS, TpslHistoryOrdersPageResp.class);
    }

    public ArrayList<TpslPendingOrderResp> getPendingTpslOrders(GetPendingTpslOrderRequest getPendingTpslOrderRequest){
        return simpleGetList(getPendingTpslOrderRequest,FuturesPath.GET_PENDING_TPSL_ORDERS, TpslPendingOrderResp.class);
    }

    public OrderIdResp modifyTpslOrder(ModifyTpslOrderRequest modifyTpslOrderRequest){
        return simplePost(modifyTpslOrderRequest,FuturesPath.MODIFY_TPSL_ORDER, OrderIdResp.class);
    }

    public OrderIdResp modifyPositionTpslOrder(PlacePositionTpslOrderRequest placePositionTpslOrderRequest){
        return simplePost(placePositionTpslOrderRequest,FuturesPath.MODIFY_POSITION_TPSL_ORDER, OrderIdResp.class);
    }

    public OrderIdResp placePositionTpslOrder(PlacePositionTpslOrderRequest placePositionTpslOrderRequest){
        return simplePost(placePositionTpslOrderRequest,FuturesPath.PLACE_POSITION_TPSL_ORDER, OrderIdResp.class);
    }


    private Headers getHeaders(String nonce, String body, TreeMap<String,String> paramMap){
        String timestamp = String.valueOf(System.currentTimeMillis());
        String sign = SignUtils.generateSign(nonce, timestamp, apiKey, paramMap, body, apiSecret);
        return Headers.of("sign",sign,"timestamp",timestamp,"nonce",nonce,"api-key",apiKey,"Accept-Language",language.getValue());
    }

    private TreeMap<String,String> getTreeMap(String ... kvs){
        TreeMap<String,String> treeMap = new TreeMap<>();
        for (int i = 0; i < kvs.length-1; i+=2) {
            treeMap.put(kvs[i],kvs[i+1]);
        }
        return treeMap;
    }

    public FuturesPrivateApiClient setNonce(String nonce) {
        this.nonce = nonce;
        return this;
    }
}
