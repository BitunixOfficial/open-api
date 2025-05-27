package com.bitunix.openapi.enums;

public enum KlineType {
    LAST_PRICE("last"),
    MARK_PRICE("mark"),
    ;

    private String value;

    KlineType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
