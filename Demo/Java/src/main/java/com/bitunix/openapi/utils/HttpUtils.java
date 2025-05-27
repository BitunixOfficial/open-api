package com.bitunix.openapi.utils;

import com.bitunix.openapi.constants.CommonResult;
import com.bitunix.openapi.exception.BitunixServerException;
import com.bitunix.openapi.exception.HttpStatusErrorException;
import okhttp3.*;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;

public class HttpUtils {
    OkHttpClient okHttpClient;

    public HttpUtils(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public <T> T getForObject(HttpUrl httpUrl, Headers headers, Class<T> respCls){
        Call call = okHttpClient.newCall(new Request(httpUrl, "GET", headers, null, Collections.emptyMap()));
        return executeCallForObject(respCls,call);
    }
    public <T> ArrayList<T> getForList(HttpUrl httpUrl,Headers headers, Class<T> respCls){
        Call call = okHttpClient.newCall(new Request(httpUrl, "GET", headers, null, Collections.emptyMap()));
        return executeCallForList(respCls, call);
    }


    public <T> ArrayList<T> postForList(HttpUrl httpUrl, Headers headers,String body,Class<T> respCls){
        Call call = okHttpClient.newCall(new Request(httpUrl, "POST", headers, RequestBody.create(body,MediaType.parse("application/json")), Collections.emptyMap()));
        return executeCallForList(respCls,call);
    }

    public <T> T postForObject(HttpUrl httpUrl, Headers headers,String body,Class<T> respCls){
        Call call = okHttpClient.newCall(new Request(httpUrl, "POST", headers, RequestBody.create(body,MediaType.parse("application/json")), Collections.emptyMap()));
        return executeCallForObject(respCls,call);
    }

    private static <T> ArrayList<T> executeCallForList(Class<T> respCls, Call call) {
        try (Response response = call.execute()){
            if (response.code() != 200){
                throw new HttpStatusErrorException(response.code());
            }
            byte[] bytes = response.body().bytes();
            String s = new String(bytes);
            CommonResult<ArrayList<T>> tCommonResult = JsonUtils.readToCommonResultList(s, respCls);
            if (tCommonResult.isOk()){
                return tCommonResult.getData();
            }
            throw new BitunixServerException(tCommonResult.getCode(), tCommonResult.getMsg());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static <T> T executeCallForObject(Class<T> respCls, Call call) {
        try (Response response = call.execute()){
            if (response.code() != 200){
                throw new HttpStatusErrorException(response.code());
            }
            byte[] bytes = response.body().bytes();
            String s = new String(bytes);
            CommonResult<T> tCommonResult = JsonUtils.readToCommonResult(s, respCls);
            if (tCommonResult.isOk()){
                return tCommonResult.getData();
            }
            throw new BitunixServerException(tCommonResult.getCode(), tCommonResult.getMsg());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
