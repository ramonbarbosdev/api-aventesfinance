package com.api_aventesfinance.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api_aventesfinance.dto.CategoriaDTO;
import com.api_aventesfinance.dto.ClienteDTO;
import com.api_aventesfinance.model.Categoria;
import com.api_aventesfinance.model.Cliente;
import com.api_aventesfinance.repository.CategoriaRepository;
import com.api_aventesfinance.repository.ClienteRepository;

import ch.qos.logback.core.model.Model;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;

import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/cliente", produces = "application/json")
@Tag(name = "Cliente")
public class ClienteController extends BaseController<Cliente, ClienteDTO, Long> {

	@Autowired
	private ClienteRepository objetoRepository;

	public ClienteController(CrudRepository<Cliente, Long> repository) {
		super(repository);
	}

	@PostMapping(value = "/cadastrar", produces = "application/json")
	public ResponseEntity<?> create(@RequestBody Cliente objeto) throws Exception {
		Optional<Cliente> objetoExistente = objetoRepository.findByNucnpjcpf(objeto.getNu_cnpjcpf());

		if (objetoExistente.isPresent()) {
			throw new Exception("Já existe cliente com esse CPF/CNPJ");
		}


		return new ResponseEntity<>(repository.save(objeto), HttpStatus.OK);

	}

}