package com.bitunix.openapi.enums;

public enum KlineInterval {
    MINUTE_1("1m","1min"),
    MINUTE_5("5m","5min"),
    MINUTE_15("15m","15min"),
    MINUTE_30("30m","30min"),
    HOUR_1("1h","60min"),
    HOUR_2("2h"),
    HOUR_4("4h"),
    HOUR_6("6h"),
    HOUR_8("8h"),
    HOUR_12("12h"),
    DAY_1("1d","1day"),
    DAY_3("3d","3day"),
    WEEK_1("1w","1week"),
    MONTH_1("1M","1month"),
    ;
    private String value;
    private String fullValue;

    KlineInterval(String value,String fullValue) {
        this.value = value;
        this.fullValue = fullValue;
    }
    KlineInterval(String value) {
        this.value = value;
        this.fullValue = value;
    }

    public String getValue() {
        return value;
    }

    public String getFullValue() {
        return fullValue;
    }
}
