package com.bitunix.openapi.utils;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class SignUtils {

    public static String generateSign(String nonce, String timestamp, String apiKey,
                                      TreeMap<String, String> queryParamsMap,
                                      String httpBody, String secretKey) {

        StringBuilder queryString = null;
        if (queryParamsMap != null && !queryParamsMap.isEmpty()) {
            queryString = new StringBuilder();
            Set<Map.Entry<String, String>> entrySet = queryParamsMap.entrySet();
            for (Map.Entry<String, String> param : entrySet) {
                if (param.getKey().equals("sign")) {
                    continue;
                }
                String value = param.getValue();

                if (value != null && ! "".equals(value)) {
                    queryString.append(param.getKey());
                    queryString.append(value);
                }
            }
        }
        String baseSignStr = nonce + timestamp + apiKey;
        if (queryString != null) {
            baseSignStr += queryString.toString();
        }
        String digest = SHAUtils.encrypt(baseSignStr, httpBody);
        return SHAUtils.encrypt(digest + secretKey);
    }
}
