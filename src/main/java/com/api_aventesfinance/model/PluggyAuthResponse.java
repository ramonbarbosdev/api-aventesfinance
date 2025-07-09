package com.api_aventesfinance.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PluggyAuthResponse {
   
    @JsonProperty("apiKey")
    private String apiKey;
    private String accessToken;

    public PluggyAuthResponse() {}

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "PluggyAuthResponse{apiKey='" + apiKey + "'}";
    }
}
