package com.api_aventesfinance.controller;



import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.api_aventesfinance.dto.CategoriaDTO;
import com.api_aventesfinance.dto.CentroCustoDTO;
import com.api_aventesfinance.dto.LancamentoDTO;
import com.api_aventesfinance.model.Categoria;
import com.api_aventesfinance.model.CentroCusto;
import com.api_aventesfinance.model.Lancamento;
import com.api_aventesfinance.repository.CentroCustoRepository;
import com.api_aventesfinance.repository.LancamentoRepository;
import com.api_aventesfinance.service.LancamentoService;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping(value = "/lancamento", produces = "application/json")
@Tag(name = "Lancamento")
public class LancamentoController  extends BaseController<Lancamento, LancamentoDTO, Long> {


	@Autowired
	private LancamentoService lancamentoService;

	@Autowired
	private LancamentoRepository objetoRepository;

	public LancamentoController(CrudRepository<Lancamento, Long> repository) {
		super(repository);
	}


	@PostMapping(value = "/cadastrar", produces = "application/json")
	public ResponseEntity<?> obterLancamentos(@RequestBody Lancamento objeto) throws Exception
	{
	
		lancamentoService.salvarItens(objeto);
		return new ResponseEntity<>(objeto, HttpStatus.OK);
	}

	@GetMapping(value = "/sequencia", produces = "application/json")
	@Operation(summary = "Gerar sequencia")
	public ResponseEntity<?> obterSequencia() {
		Long ultima_sequencia = Optional.ofNullable(objetoRepository.obterSequencial()).orElse(0L);

		Long sq_sequencia = ultima_sequencia + 1;
	    String resposta = String.format("%03d", sq_sequencia); 

		return new ResponseEntity<>(Map.of("sequencia" ,resposta), HttpStatus.OK);
	}

}