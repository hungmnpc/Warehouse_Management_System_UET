package com.monopoco.musicmp4.Models;

public class ResponseCommon<T> {

    private Result result;

    private T data;

    public ResponseCommon(Result result, T data) {
        this.result = result;
        this.data = data;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
