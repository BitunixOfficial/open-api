package com.bitunix.openapi.client;

import com.bitunix.openapi.constants.FuturesPath;
import com.bitunix.openapi.constants.ServerConfig;
import com.bitunix.openapi.request.KlineRequest;
import com.bitunix.openapi.response.*;
import com.bitunix.openapi.utils.HttpUtils;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.time.Duration;
import java.util.*;

public class FuturesPublicApiClient {

    private final HttpUtils httpUtils;
    private static final OkHttpClient defaultOkHttpClient = new OkHttpClient.Builder()
            .callTimeout(Duration.ofSeconds(60L))
            .connectTimeout(Duration.ofSeconds(60L))
            .readTimeout(Duration.ofSeconds(60L))
            .build();

    public FuturesPublicApiClient() {
        this.httpUtils = new HttpUtils(defaultOkHttpClient);
    }
    public FuturesPublicApiClient(OkHttpClient okHttpClient) {
        this.httpUtils = new HttpUtils(okHttpClient);
    }

    public ArrayList<TradingPair> getTradingPairs(){
        return getTradingPairs(Collections.emptySet());
    }

    public ArrayList<TradingPair> getTradingPairs(Set<String> symbols){
        HttpUrl.Builder builder = new HttpUrl.Builder()
                .scheme(ServerConfig.HTTPS_SCHEMA)
                .host(ServerConfig.HOST)
                .port(ServerConfig.port)
                .addPathSegment(FuturesPath.GET_TRADING_PAIRS);
        if (!symbols.isEmpty()){
            builder.addQueryParameter("symbols", String.join(",", symbols.toArray(new CharSequence[0])));
        }
        return httpUtils.getForList(builder.build(), Headers.of(),TradingPair.class);
    }

    public ArrayList<Ticker> getTickers(Set<String> symbols){
        HttpUrl.Builder builder = new HttpUrl.Builder()
                .scheme(ServerConfig.HTTPS_SCHEMA)
                .host(ServerConfig.HOST)
                .port(ServerConfig.port)
                .addPathSegment(FuturesPath.GET_TICKERS);
        if (!symbols.isEmpty()){
            builder.addQueryParameter("symbols", String.join(",", symbols.toArray(new CharSequence[0])));
        }
        return httpUtils.getForList(builder.build(), Headers.of(), Ticker.class);
    }

    public ArrayList<Kline> getKline(KlineRequest klineRequest){
        HttpUrl.Builder builder = new HttpUrl.Builder()
                .scheme(ServerConfig.HTTPS_SCHEMA)
                .host(ServerConfig.HOST)
                .port(ServerConfig.port)
                .addPathSegment(FuturesPath.GET_KLINE);
        builder.addQueryParameter("symbol", klineRequest.getSymbol());
        builder.addQueryParameter("interval", klineRequest.getInterval());
        builder.addQueryParameter("type", klineRequest.getKlineType().name());
        if (klineRequest.getStartTime() != null){
            builder.addQueryParameter("startTime", klineRequest.getStartTime().toString());
        }
        if (klineRequest.getEndTime() != null){
            builder.addQueryParameter("endTime", klineRequest.getEndTime().toString());
        }
        if (klineRequest.getLimit() != null){
            builder.addQueryParameter("limit", klineRequest.getLimit().toString());
        }

        return httpUtils.getForList(builder.build(), Headers.of(), Kline.class);
    }

    public FundingRate getFundingRate(String symbol){
        HttpUrl.Builder builder = new HttpUrl.Builder()
                .scheme(ServerConfig.HTTPS_SCHEMA)
                .host(ServerConfig.HOST)
                .port(ServerConfig.port)
                .addPathSegment(FuturesPath.GET_FUNDING_RATE);
        builder.addQueryParameter("symbol",symbol);
        return httpUtils.getForObject(builder.build(), Headers.of(), FundingRate.class);
    }

    public Depth getDepth(String symbol, String limit){
        HttpUrl.Builder builder = new HttpUrl.Builder()
                .scheme(ServerConfig.HTTPS_SCHEMA)
                .host(ServerConfig.HOST)
                .port(ServerConfig.port)
                .addPathSegment(FuturesPath.GET_DEPTH);
        builder.addQueryParameter("symbol",symbol);
        if (limit != null) {
            builder.addQueryParameter("limit", limit);
        }
        return httpUtils.getForObject(builder.build(), Headers.of(), Depth.class);
    }


}
