package com.bitunix.openapi.exception;

public class BitunixServerException extends RuntimeException{

    private Integer code;

    public BitunixServerException(Integer code, String msg) {
        super("["+code+ "] " +msg);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
