package com.bitunix.openapi.request;


public class PageRequest {

    private Integer skip = 0;

    private Integer limit = 100;

    public Integer getSkip() {
        return skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
