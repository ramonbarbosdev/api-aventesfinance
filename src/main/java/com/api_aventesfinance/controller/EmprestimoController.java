package com.api_aventesfinance.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api_aventesfinance.dto.EmprestimoDTO;
import com.api_aventesfinance.dto.LancamentoDTO;
import com.api_aventesfinance.enums.StatusEmprestimo;
import com.api_aventesfinance.enums.TipoEmprestimo;
import com.api_aventesfinance.model.Emprestimo;
import com.api_aventesfinance.model.Lancamento;
import com.api_aventesfinance.repository.EmprestimoRepository;
import com.api_aventesfinance.repository.ItemEmprestimoRepository;
import com.api_aventesfinance.service.EmprestimoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/emprestimo", produces = "application/json")
@Tag(name = "Emprestimo")
public class EmprestimoController extends BaseController<Emprestimo, EmprestimoDTO, Long> {

    @Autowired
    private EmprestimoRepository repository;

    public EmprestimoController(CrudRepository<Emprestimo, Long> repository) {
        super(repository);
    }

    @Autowired
    private ItemEmprestimoRepository itemEmprestimoRepository;

    @Autowired
    private EmprestimoService service;

    @PostMapping(value = "/cadastrar", produces = "application/json")
	public ResponseEntity<?> cadastrarMestreDatalhe(@RequestBody Emprestimo objeto) throws Exception {

		service.salvarItens(objeto);
		return new ResponseEntity<>(objeto, HttpStatus.OK);
	}



    @GetMapping(value = "/sequencia", produces = "application/json")
    @Operation(summary = "Gerar sequencia")
    public ResponseEntity<?> obterSequencia() {
        Long ultima_sequencia = Optional.ofNullable(repository.obterSequencial()).orElse(0L);

        Long sq_sequencia = ultima_sequencia + 1;
        String resposta = "%03d".formatted(sq_sequencia);

        return new ResponseEntity<>(Map.of("sequencia", resposta), HttpStatus.OK);
    }

    @GetMapping("/tipo-emprestimo")
    public ResponseEntity<TipoEmprestimo[]> obterTipoEmprestimo() {
        return ResponseEntity.ok(TipoEmprestimo.values());
    }

    @GetMapping("/status-emprestimo")
    public ResponseEntity<StatusEmprestimo[]> obterStatusEmprestimo() {
        return ResponseEntity.ok(StatusEmprestimo.values());
    }

    @DeleteMapping(value = "/deletar/{id}", produces = "application/json")
	public ResponseEntity<?> deletar(@PathVariable Long id) {


		service.excluir(id);
		return new ResponseEntity<>(Map.of("message", "Deletado com sucesso!"), HttpStatus.OK);

	}

}
