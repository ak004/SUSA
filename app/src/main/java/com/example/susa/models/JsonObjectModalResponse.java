package com.example.susa.models;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class JsonObjectModalResponse {
    @SerializedName("success")
    private boolean success;
    @SerializedName("record")
    private JsonObject record;
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public JsonObject getRecord() {
        return record;
    }

    public void setRecord(JsonObject record) {
        this.record = record;
    }
}

