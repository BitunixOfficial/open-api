package com.bitunix.openapi.request;

import com.bitunix.openapi.enums.KlineInterval;
import com.bitunix.openapi.enums.KlineType;

public class KlineRequest {

    private String symbol;

    private Long startTime;

    private Long endTime;

    private String interval;

    private Integer limit;

    private KlineType klineType;

    public void setInterval(KlineInterval klineInterval) {
        this.interval = klineInterval.getValue();
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public void setKlineType(KlineType klineType) {
        this.klineType = klineType;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getSymbol() {
        return symbol;
    }

    public Long getStartTime() {
        return startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public String getInterval() {
        return interval;
    }

    public Integer getLimit() {
        return limit;
    }

    public KlineType getKlineType() {
        return klineType;
    }
}
