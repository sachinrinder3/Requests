package com.example.android.requests.utils;


public class ServerResponse<T> {

    private T data;
    private String message;
    private int httpStatusCode;

    public ServerResponse(T data, String message, int httpStatusCode) {
        this.data = data;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

    public T getData() {
        return data;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getMessage() {
        return message;
    }
}