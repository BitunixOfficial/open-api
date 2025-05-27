package com.bitunix.openapi.response.ws;

import com.bitunix.openapi.request.ws.BasicWsEntity;

import java.util.Map;

public class WsConnectSuc extends BasicWsEntity {

    private Map data;

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }
}
