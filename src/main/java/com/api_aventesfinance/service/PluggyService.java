package com.api_aventesfinance.service;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.api_aventesfinance.dto.PluggyAuthResponse;
import com.api_aventesfinance.dto.PluggyAuthTokenResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PluggyService {

    public Map<String, Object> criarItemSandbox(String apiKey, Long id_item) {
        String url = "https://api.pluggy.ai/items";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Map<String, Object> body = Map.of(
        // "connectorId", id_item,
        // "parameters", Map.of(
        // "user", "user-ok",
        // "password", "pass-ok"));

        Map<String, Object> body = Map.of(
                "connectorId", id_item,
                "parameters", Map.of(
                        "validation", "85778905548",
                        "password", "pass-ok"));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = new RestTemplate()
                .postForEntity(url, entity, Map.class);

        return response.getBody();
    }

    public String gerarConnectToken(String apiKey, String clientUserId) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-KEY", apiKey);

        Map<String, Object> options = Map.of("clientUserId", clientUserId);
        Map<String, Object> body = Map.of("options", options);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<PluggyAuthTokenResponse> response = restTemplate.postForEntity(
                    "https://api.pluggy.ai/connect_token",
                    entity,
                    PluggyAuthTokenResponse.class);

            PluggyAuthTokenResponse auth = response.getBody();

            return auth.getAccessToken();

        } catch (HttpClientErrorException e) {
            return e.getMessage();
        }

    }

    public ResponseEntity<Map> obterConta(String apiKey, String id_item) {
        String url = "https://api.pluggy.ai/accounts?itemId=" + id_item;

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(null, headers);

        ResponseEntity<Map> response = new RestTemplate().exchange(url, HttpMethod.GET, entity, Map.class);

        return response;

    }

    public JsonNode responseError(HttpClientErrorException e) throws JsonMappingException, JsonProcessingException {

        String responseBody = e.getResponseBodyAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        return jsonNode;
    }

}
