package com.api_aventesfinance.dto;

public class PluggyAuthRequest {
    public String clientId;
    public String clientSecret;

    public PluggyAuthRequest(String clientId, String clientSecret )
    {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String getClientId() {
        return clientId;
    }
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    public String getClientSecret() {
        return clientSecret;
    }
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
    
}
