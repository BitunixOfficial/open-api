package com.bitunix.openapi.exception;

public class HttpStatusErrorException extends RuntimeException{

    private Integer code;

    public HttpStatusErrorException(Integer code) {
        super("request fail, http status code :"+code);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
