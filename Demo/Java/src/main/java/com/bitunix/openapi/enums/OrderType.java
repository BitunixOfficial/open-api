package com.bitunix.openapi.enums;


import java.util.Arrays;

public enum OrderType{
    LIMIT(1),
    MARKET(2);

    private int type;

    OrderType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static OrderType fromValue(Integer type) {
        return Arrays.stream(values()).filter(e -> e.type == type).findFirst().orElse(null);
    }
}
