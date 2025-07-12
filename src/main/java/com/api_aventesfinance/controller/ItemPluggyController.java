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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.api_aventesfinance.dto.PluggyItemRequest;
import com.api_aventesfinance.dto.PluggyItemResponse;
import com.api_aventesfinance.model.ItemPluggy;
import com.api_aventesfinance.repository.ItemPluggyRepository;
import com.api_aventesfinance.service.PluggyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/item-pluggy")
@Tag(name = "Item Pluggy")
public class ItemPluggyController {

    @Autowired
    private PluggyService pluggyService;

    @Autowired
	private ItemPluggyRepository itemPluggyRepository;


    
	@PostMapping(value = "/criar-item")
	public ResponseEntity<?> criarItem(@RequestBody PluggyItemRequest request) {

		// nubank - 612

		if (request.getAccessToken() == null || request.getAccessToken().isEmpty()) {
			Map<String, Object> message = Map.of("message", "Token nao foi informado!");
			return new ResponseEntity<>(message, HttpStatus.OK);
		}

		RestTemplate restTemplate = new RestTemplate();

		Map<String, Object> credentials = Map.of(
				"cpf", request.getCpf(),
				"agency", request.getAgency(),
				"account", request.getAccount(),
				"password", request.getPassword());

		Map<String, Object> body = Map.of(
				"connectorId", request.getConnectorId(),
				"parameters", credentials);

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-API-KEY", request.getAccessToken());
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

		ResponseEntity<PluggyItemResponse> response = restTemplate.postForEntity(
				"https://api.pluggy.ai/items",
				entity,
				PluggyItemResponse.class);

		PluggyItemResponse itemResponse = response.getBody();

		ItemPluggy objeto = new ItemPluggy();
		objeto.setId_item(itemResponse.getId());
		objeto.setConnectorId(itemResponse.getConnector().getId());
        objeto.setInstitutionUrl(itemResponse.getConnector().getInstitutionUrl());
        objeto.setName(itemResponse.getConnector().getName());
        objeto.setType(itemResponse.getConnector().getType());

		itemPluggyRepository.save(objeto);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@GetMapping(value = "/" , produces = "application/json")
	public ResponseEntity<?> obterItens()
			throws JsonMappingException, JsonProcessingException {

        List<ItemPluggy> objetos = (List<ItemPluggy>) itemPluggyRepository.findAll();
        
        return new ResponseEntity<>(objetos, HttpStatus.OK);

	}
	
	@GetMapping(value = "/obter-item/{id_item}/{apiKey}")
	public ResponseEntity<?> obterItemId(@PathVariable String id_item, @PathVariable String apiKey)
			throws JsonMappingException, JsonProcessingException {

		String url = "https://api.pluggy.ai/items/" + id_item;

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-API-KEY", apiKey);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(null, headers);

		try {
			ResponseEntity<Map> response = new RestTemplate().exchange(url, HttpMethod.GET, entity, Map.class);
			return new ResponseEntity<>(response.getBody(), HttpStatus.OK);

		} catch (HttpClientErrorException e) {
			JsonNode error = pluggyService.responseError(e);
			return new ResponseEntity<>(error, HttpStatus.valueOf(e.getStatusCode().value()));
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping(value = "/{id_item}/{apiKey}")
	public ResponseEntity<?> excluirItens(@PathVariable String id_item, @PathVariable String apiKey)
			throws JsonMappingException, JsonProcessingException {

		String url = "https://api.pluggy.ai/items/" + id_item;

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-API-KEY", apiKey);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(null, headers);

		try {
			ResponseEntity<Map> response = new RestTemplate().exchange(url, HttpMethod.DELETE, entity, Map.class);
			
			itemPluggyRepository.deleteById(id_item);

			 return ResponseEntity.ok(Map.of("message", "Item excluido com sucesso."));

		} catch (HttpClientErrorException e) {
			JsonNode error = pluggyService.responseError(e);
			return new ResponseEntity<>(error, HttpStatus.valueOf(e.getStatusCode().value()));
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
