package com.bitunix.openapi.test;

import com.bitunix.openapi.client.FuturesPublicApiClient;
import com.bitunix.openapi.response.TradingPair;
import com.bitunix.openapi.utils.JsonUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

public class GetTradingPairTest {


    @Test
    public void test() {
        FuturesPublicApiClient futuresPublicApiClient = new FuturesPublicApiClient();
        ArrayList<TradingPair> tradingPairs = futuresPublicApiClient.getTradingPairs(Collections.singleton("BTCUSDT"));
        System.out.println(JsonUtils.toJsonString(tradingPairs));
    }
}
