package com.api_aventesfinance.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import com.api_aventesfinance.dto.PluggyPaginacaoResponse;
import com.api_aventesfinance.dto.PluggyTransacaoResponse;
import com.api_aventesfinance.model.ItemPluggy;
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

		ResponseEntity<Map> response = pluggyService.obterConta(apiKey, id_item);

		return new ResponseEntity<>(response.getBody(), HttpStatus.OK);

	}

	// TRANSACOES

	@GetMapping(value = "/{apiKey}/{pageSize}/{page}")
	public ResponseEntity<?> obterTodasTransacao( @PathVariable String apiKey,@PathVariable Integer pageSize,@PathVariable Integer page) {

		String accountId = "";
		Optional<ItemPluggy> mainAtual = itemPluggyRepository.findByFlMainTrue();
		if (mainAtual.isPresent()) {

			ResponseEntity<Map> responseConta = pluggyService.obterConta(apiKey, mainAtual.get().getId_item());

			Object resultsConta = responseConta.getBody().get("results");

			List<Map<String, Object>> resultList = (List<Map<String, Object>>) resultsConta;

			// 0 - credito, 1 - banco

			if (resultList.get(1).get("id") != null) {
				accountId = (String) resultList.get(0).get("id");
			}
			
			String params = "";
			if(accountId != null) params = "?accountId=" +accountId;
			if(pageSize != null) params = params +"&pageSize="+pageSize;
			if(page != null) params = params +"&page="+page;


			String url = "https://api.pluggy.ai/transactions" + params;

			HttpHeaders headers = new HttpHeaders();
			headers.set("X-API-KEY", apiKey);
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<Map<String, Object>> entity = new HttpEntity<>(null, headers);

			ResponseEntity<Map> response = new RestTemplate().exchange(url, HttpMethod.GET, entity, Map.class);

			ObjectMapper mapper = new ObjectMapper();

			Map<String, Object> responseBody = response.getBody();

			PluggyPaginacaoResponse paginacao = mapper.convertValue(
					responseBody,
					PluggyPaginacaoResponse.class);

			return new ResponseEntity<>(paginacao, HttpStatus.OK);
		}

		return ResponseEntity.ok(Map.of("message", "Conta principal nao definida!"));

	}

}