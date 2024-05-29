package com.monopoco.musicmp4.Models;

import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("responseCode")
    String responseCode;

    @SerializedName("messgae")
    private String message;

    @SerializedName("ok")
    private boolean ok;

    public Result(String responseCode, String message, boolean ok) {
        this.responseCode = responseCode;
        this.message = message;
        this.ok = ok;
    }


    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
}
