package com.api_aventesfinance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PluggyAuthResponse {

    @JsonProperty("apiKey")
    private String apiKey;

    public PluggyAuthResponse() {}

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String toString() {
        return "PluggyAuthResponse{apiKey='" + apiKey + "'}";
    }
}
