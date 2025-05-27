package com.bitunix.openapi.request.ws;

import com.bitunix.openapi.enums.KlineInterval;
import com.bitunix.openapi.enums.KlineType;

public class KlineSubArg extends BasicSubArg{

    private String symbol;

    public KlineSubArg(KlineType klineType,KlineInterval klineInterval,String symbol) {
        super(klineType.getValue()+"_kline_"+klineInterval.getFullValue());
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}



