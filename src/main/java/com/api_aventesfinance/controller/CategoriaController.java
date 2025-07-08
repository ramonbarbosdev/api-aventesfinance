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
import com.api_aventesfinance.model.Categoria;
import com.api_aventesfinance.repository.CategoriaRepository;

import ch.qos.logback.core.model.Model;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;

import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/categoria", produces = "application/json")
@Tag(name = "Categoria")
public class CategoriaController extends BaseController<Categoria, CategoriaDTO, Long> {
	@Autowired
	private CategoriaRepository objetoRepository;

	public CategoriaController(CrudRepository<Categoria, Long> repository) {
		super(repository);
	}

	@GetMapping(value = "/sequencia", produces = "application/json")
	@Operation(summary = "Gerar sequencia")
	public ResponseEntity<?> obterSequencia() {
		Long ultima_sequencia = objetoRepository.obterSequencial();

		Long sq_sequencia = ultima_sequencia + 1;

		return new ResponseEntity<>(sq_sequencia, HttpStatus.OK);
	}

}