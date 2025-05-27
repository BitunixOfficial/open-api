package com.bitunix.openapi.test;

import com.bitunix.openapi.client.FuturesPrivateApiClient;
import com.bitunix.openapi.enums.Language;
import com.bitunix.openapi.response.Account;
import com.bitunix.openapi.utils.JsonUtils;
import org.junit.jupiter.api.Test;

public class GetAccountTest {

    @Test
    public void test() {
        FuturesPrivateApiClient futuresPrivateApiClient = new FuturesPrivateApiClient("1457495895d39196b7cea149bf6cec79","f58162e28c6479e9c3f0f7354ae21c35", Language.Uzbek);
        Account account = futuresPrivateApiClient.getAccount("USDT");
        System.out.println(JsonUtils.toJsonString(account));
    }
}
