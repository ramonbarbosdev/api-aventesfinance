package com.api_aventesfinance.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.api_aventesfinance.dto.PluggyTransacaoResponse;
import com.api_aventesfinance.repository.ItemPluggyRepository;
import com.api_aventesfinance.service.PluggyService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/transacao-pluggy")
@Tag(name = "Transação Pluggy")
public class TransacaoPluggyController {

	@Autowired
	private PluggyService pluggyService;

	@Autowired
	private ItemPluggyRepository itemPluggyRepository;

	// CONTAS

	@GetMapping(value = "/conta/{id_item}/{apiKey}")
	public ResponseEntity<?> obterConta(@PathVariable String id_item, @PathVariable String apiKey) {

		String url = "https://api.pluggy.ai/accounts?itemId=" + id_item;

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-API-KEY", apiKey);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(null, headers);

		ResponseEntity<Map> response = new RestTemplate().exchange(url, HttpMethod.GET, entity, Map.class);

		return new ResponseEntity<>(response.getBody(), HttpStatus.OK);

	}

	// TRANSACOES

	@GetMapping(value = "/transacao/{accountId}/{apiKey}")
	public ResponseEntity<?> obterTransacao(@PathVariable String accountId, @PathVariable String apiKey) {

		String url = "https://api.pluggy.ai/transactions?accountId=" + accountId;

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-API-KEY", apiKey);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(null, headers);

		ResponseEntity<Map> response = new RestTemplate().exchange(url, HttpMethod.GET, entity, Map.class);

		Object results = response.getBody().get("results");
		ObjectMapper mapper = new ObjectMapper();
		List<PluggyTransacaoResponse> transacoes = mapper.convertValue(
				results,
				new TypeReference<List<PluggyTransacaoResponse>>() {
				});
		
		
		return new ResponseEntity<>(transacoes, HttpStatus.OK);

	}

}