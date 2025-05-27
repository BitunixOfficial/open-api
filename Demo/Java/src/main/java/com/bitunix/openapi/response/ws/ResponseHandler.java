package com.bitunix.openapi.response.ws;

public interface ResponseHandler<T> {

    void handle(T t);
}
