package com.api_aventesfinance.dto;

public class PluggyItemRequest {
    public String accessToken;
    public Integer connectorId;
    public String cpf;
    public String agency;
    public String account;
    public String password;

    public PluggyItemRequest(String accessToken, Integer connectorId, String cpf, String agency, String account,
            String password) {
        this.accessToken = accessToken;
        this.connectorId = connectorId;
        this.cpf = cpf;
        this.agency = agency;
        this.account = account;
        this.password = password;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getConnectorId() {
        return connectorId;
    }

    public void setConnectorId(Integer connectorId) {
        this.connectorId = connectorId;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
