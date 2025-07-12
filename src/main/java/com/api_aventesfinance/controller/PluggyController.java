package com.api_aventesfinance.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.api_aventesfinance.dto.PluggyAuthRequest;
import com.api_aventesfinance.dto.PluggyAuthResponse;
import com.api_aventesfinance.dto.PluggyItemRequest;
import com.api_aventesfinance.dto.UsuarioDTO;
import com.api_aventesfinance.model.Usuario;
import com.api_aventesfinance.repository.UsuarioRepository;
import com.api_aventesfinance.service.PluggyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/pluggy")
@Tag(name = "Pluggy")
public class PluggyController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PluggyService pluggyService;

	@GetMapping(value = "/obter-token/{id_usuario}")
	public ResponseEntity<?> obterAcessoToken(@PathVariable Long id_usuario)
			throws JsonMappingException, JsonProcessingException {
		RestTemplate restTemplate = new RestTemplate();

		Optional<Usuario> usuario = usuarioRepository.findById(id_usuario);

		String clientId = "4faf6b35-526c-4c1c-865b-fc4014caa5c8";
		String clientSecret = "9b1fae42-1bcf-45f4-bee6-a1e095532f4c";
		String clientUserId = usuario.get().getLogin();

		PluggyAuthRequest request = new PluggyAuthRequest(clientId, clientSecret);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<PluggyAuthRequest> entity = new HttpEntity<>(request, headers);

		try {
			ResponseEntity<PluggyAuthResponse> response = restTemplate.postForEntity(
					"https://api.pluggy.ai/auth", entity, PluggyAuthResponse.class);

			PluggyAuthResponse auth = response.getBody();
			String apiKey = auth.getApiKey();

			String accessToken = "";
			accessToken = pluggyService.gerarConnectToken(apiKey, clientUserId);

			return ResponseEntity
					.ok(Map.of("apiKey", apiKey, "accessToken", accessToken, "clientUserId", clientUserId));

		} catch (Exception e) {

			return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
		}

	}

	@PostMapping(value = "/criar-item")
	public ResponseEntity<?> criarItem(@RequestBody PluggyItemRequest request) {

		// nubank - 612

		if (request.getAccessToken() == null || request.getAccessToken().isEmpty()) {
			Map<String, Object> message = Map.of("message", "Token nao foi informado!");

			return new ResponseEntity<>(message, HttpStatus.OK);

		}

		RestTemplate restTemplate = new RestTemplate();

		// Monta os dados de credenciais (ex: CPF)
		Map<String, Object> credentials = Map.of(
			"cpf", request.getCpf(),
			"agency", request.getAgency(),
			"account", request.getAccount(),
			"password", request.getPassword()
			);

		Map<String, Object> body = Map.of(
				"connectorId", request.getConnectorId(),
				"parameters", credentials);

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-API-KEY", request.getAccessToken());
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

		ResponseEntity<Map> response = restTemplate.postForEntity(
				"https://api.pluggy.ai/items",
				entity,
				Map.class);

		response.getBody();

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@GetMapping(value = "/obter-item/{apiKey}")
	public ResponseEntity<?> obterItens(@PathVariable String apiKey) {

		String url = "https://api.pluggy.ai/items";

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-API-KEY", apiKey);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(null, headers);

		ResponseEntity<Map> response = new RestTemplate().exchange(url, HttpMethod.GET, entity, Map.class);

		return new ResponseEntity<>(response.getBody(), HttpStatus.OK);

	}

	@GetMapping(value = "/contas/{apiKey}")
	public ResponseEntity<?> obterContas(@PathVariable String apiKey) {

		String url = "https://api.pluggy.ai/connectors";

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-API-KEY", apiKey);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(null, headers);

		ResponseEntity<Map> response = new RestTemplate().exchange(url, HttpMethod.GET, entity, Map.class);

		return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
	}

	@GetMapping(value = "/conectores/{apiKey}")
	public ResponseEntity<?> obterConectores(@PathVariable String apiKey) {

		String url = "https://api.pluggy.ai/connectors";

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-API-KEY", apiKey);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(null, headers);

		ResponseEntity<Map> response = new RestTemplate().exchange(url, HttpMethod.GET, entity, Map.class);

		return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
	}

	@GetMapping(value = "/conectores/{apiKey}/{id}")
	public ResponseEntity<?> obterConectoresPorId(@PathVariable String apiKey, @PathVariable Long id) {

		String url = "https://api.pluggy.ai/connectors/" + id;

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-API-KEY", apiKey);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(null, headers);

		ResponseEntity<Map> response = new RestTemplate().exchange(url, HttpMethod.GET, entity, Map.class);

		return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
	}

	@GetMapping(value = "/webhooks/{apiKey}")
	public ResponseEntity<?> obterGanchos(@PathVariable String apiKey) {

		String url = "https://api.pluggy.ai/webhooks";

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-API-KEY", apiKey);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(null, headers);

		ResponseEntity<Map> response = new RestTemplate().exchange(url, HttpMethod.GET, entity, Map.class);

		return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
	}

}