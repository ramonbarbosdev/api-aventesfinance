package com.api_aventesfinance.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import com.api_aventesfinance.repository.ItemLancamentoRepository;
import com.api_aventesfinance.repository.LancamentoRepository;
import com.api_aventesfinance.service.LancamentoService;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(value = "/lancamento", produces = "application/json")
@Tag(name = "Lancamento")
public class LancamentoController extends BaseController<Lancamento, LancamentoDTO, Long> {

	@Autowired
	private LancamentoService lancamentoService;

	@Autowired
	private LancamentoRepository objetoRepository;

	@Autowired
	private ItemLancamentoRepository itemLancamentoRepository;

	public LancamentoController(CrudRepository<Lancamento, Long> repository) {
		super(repository);
	}

	@PostMapping(value = "/cadastrar", produces = "application/json")
	public ResponseEntity<?> cadastrarLanc(@RequestBody Lancamento objeto) throws Exception {

		lancamentoService.salvarItens(objeto);
		return new ResponseEntity<>(objeto, HttpStatus.OK);
	}

	@GetMapping(value = "/lancamento-existente/{id_centrocusto}/{dt_anomes}",  produces = "application/json")
	public ResponseEntity<?> obterLancamentoExistente(@PathVariable Long id_centrocusto , @PathVariable String dt_anomes) {

		Optional<Lancamento> objeto = objetoRepository.existeLancamentoPorCentroCustoMes(dt_anomes,
                id_centrocusto, null);
		return new ResponseEntity<>(objeto, HttpStatus.OK);
	}
	

	@DeleteMapping(value = "/deletar/{id}", produces = "application/json")
	public ResponseEntity<?> deletar(@PathVariable Long id) {


		lancamentoService.excluir(id);
		return new ResponseEntity<>(Map.of("message", "Deletado com sucesso!"), HttpStatus.OK);

	}

	@GetMapping(value = "/sequencia", produces = "application/json")
	@Operation(summary = "Gerar sequencia")
	public ResponseEntity<?> obterSequencia() {
		Long ultima_sequencia = Optional.ofNullable(objetoRepository.obterSequencial()).orElse(0L);

		Long sq_sequencia = ultima_sequencia + 1;
		String resposta = String.format("%03d", sq_sequencia);

		return new ResponseEntity<>(Map.of("sequencia", resposta), HttpStatus.OK);
	}

	@GetMapping(value = "/sequencia-detalhe/{id}", produces = "application/json")
	@Operation(summary = "Gerar sequencia")
	public ResponseEntity<?> obterSequenciaDetalhe(@PathVariable Long id) {

		Long ultima_sequencia;

		if (id != 0) {
			ultima_sequencia = Optional.ofNullable(itemLancamentoRepository.obterSequencialById(Long.valueOf(id)))
					.orElse(0L);

		} else {
			ultima_sequencia = (long) 0;

		}

		Long sq_sequencia = ultima_sequencia + 1;
		String resposta = String.format("%03d", sq_sequencia);

		return new ResponseEntity<>(Map.of("sequencia", resposta), HttpStatus.OK);
	}

}