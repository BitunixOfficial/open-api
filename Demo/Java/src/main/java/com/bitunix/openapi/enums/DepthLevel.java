package com.bitunix.openapi.enums;

public enum DepthLevel {
    ONE(1),
    FIVE(5),
    FIFTEEN(15)
    ;
    private int count;


    DepthLevel(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
