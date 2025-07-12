package com.api_aventesfinance.dto;

public class PluggyItemResponse {
    public String id;
    public Long connectorId;
    private PluggyConnectorResponse connector;


    public PluggyItemResponse(){}

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public Long getConnectorId() {
        return connectorId;
    }
    public void setConnectorId(Long connectorId) {
        this.connectorId = connectorId;
    }

    public PluggyConnectorResponse getConnector() {
        return connector;
    }
    public void setConnector(PluggyConnectorResponse connector) {
        this.connector = connector;
    }
    
       


}
