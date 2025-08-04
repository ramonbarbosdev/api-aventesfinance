package com.api_aventesfinance.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api_aventesfinance.dto.CategoriaDTO;
import com.api_aventesfinance.dto.CentroCustoDTO;
import com.api_aventesfinance.model.Categoria;
import com.api_aventesfinance.model.CentroCusto;
import com.api_aventesfinance.repository.CentroCustoRepository;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/centrocusto", produces = "application/json")
@Tag(name = "CentroCusto")
public class CentroCustoController {
	@Autowired
	private CentroCustoRepository repository;

	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<List<?>> obterTodos(@RequestHeader(value = "X-Cliente", required = false) String id_cliente,
			@RequestHeader(value = "X-Competencia", required = false) String competencia) {
		List<CentroCusto> entidades = (List<CentroCusto>) repository.findByAllCliente(Long.valueOf(id_cliente));

		return new ResponseEntity<>(entidades, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<?> obterPorId(@PathVariable Long id) {
		Optional<CentroCusto> objeto = repository.findById(id);

		return new ResponseEntity<>(objeto, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'GESTOR', 'USER')")
	@PostMapping(value = "/", produces = "application/json")
	public ResponseEntity<?> cadastrar(@RequestBody CentroCusto objeto,
			@RequestHeader(value = "X-Cliente", required = false) String id_cliente,
			@RequestHeader(value = "X-Competencia", required = false) String competencia) {
			
		objeto.setId_cliente(Long.valueOf(id_cliente));
		CentroCusto objetoSalvo = repository.save(objeto);

		return new ResponseEntity<>(objetoSalvo, HttpStatus.CREATED);
	}

		@PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
	@DeleteMapping(value = "/{id}", produces = "application/text")
	public ResponseEntity<?> delete(@PathVariable Long id) throws Exception {
		repository.deleteById(id);

		return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Registro deletado!\"}");

	}

	@GetMapping(value = "/sequencia", produces = "application/json")
	@Operation(summary = "Gerar sequencia")
	public ResponseEntity<?> obterSequencia(@RequestHeader(value = "X-Cliente", required = false) String id_cliente) {
		Long ultima_sequencia = Optional
				.ofNullable(repository.obterSequencial(Long.valueOf(id_cliente))).orElse(0L);

		Long sq_sequencia = ultima_sequencia + 1;
		String resposta = "%03d".formatted(sq_sequencia);

		return new ResponseEntity<>(Map.of("sequencia", resposta), HttpStatus.OK);
	}

}