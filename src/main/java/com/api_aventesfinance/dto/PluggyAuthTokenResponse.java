package com.api_aventesfinance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PluggyAuthTokenResponse {

    @JsonProperty("accessToken")
    private String accessToken;

    public PluggyAuthTokenResponse( )
    {
        
    }

    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "PluggyAuthResponse{accessToken='" + accessToken + "'}";
    }
    
}
