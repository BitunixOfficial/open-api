package com.bitunix.openapi.enums;



public enum TpslOrderType {

    LIMIT(1),
    MARKET(2);

    private final Integer value;

    TpslOrderType(Integer value) {
        this.value = value;
    }

    public static TpslOrderType fromValue(Integer value) {
        for (TpslOrderType type : TpslOrderType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return MARKET;
    }

    public Integer getValue() {
        return value;
    }
}
