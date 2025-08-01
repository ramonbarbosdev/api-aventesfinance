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
    private EmprestimoRepository objetoRepository;

    @Autowired
    private ItemEmprestimoRepository itemEmprestimoRepository;

    @Autowired
    private EmprestimoService service;

    public EmprestimoController(CrudRepository<Emprestimo, Long> repository) {
        super(repository);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR', 'USER')")
    @PostMapping(value = "/cadastrar", produces = "application/json")
    public ResponseEntity<?> cadastrarMestreDatalhe(@RequestBody Emprestimo objeto,
            @RequestHeader(value = "X-Cliente", required = false) String id_cliente) throws Exception {

        service.salvarItens(objeto, Long.valueOf(id_cliente));
        return new ResponseEntity<>(objeto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR', 'USER')")
    @GetMapping(value = "/lista-por-competencia/", produces = "application/json")
    public ResponseEntity<?> obterObjetoCompetenica(
            @RequestHeader(value = "X-Cliente", required = false) String id_cliente,
            @RequestHeader(value = "X-Competencia", required = false) String competencia) {

        if (competencia == null || competencia.isEmpty()) {
            return new ResponseEntity<>(Map.of("message", "Competência não foi definida!"), HttpStatus.FOUND);

        }
        List<Emprestimo> objeto = objetoRepository.buscarObjetoCompetancia(competencia, Long.valueOf(id_cliente));

        return new ResponseEntity<>(objeto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR', 'USER')")
    @GetMapping(value = "/lista-por-competencia/{id}", produces = "application/json")
    public ResponseEntity<?> obterObjetoCompetenicaId(
            @RequestHeader(value = "X-Cliente", required = false) String id_cliente,
            @RequestHeader(value = "X-Competencia", required = false) String competencia, @PathVariable Long id) {

        if (competencia == null || competencia.isEmpty()) {
            return new ResponseEntity<>(Map.of("message", "Competência não foi definida!"), HttpStatus.FOUND);

        }
        Emprestimo objeto = objetoRepository.buscarObjetoCompetanciaId(competencia, id, Long.valueOf(id_cliente));

        return new ResponseEntity<>(objeto, HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR', 'USER')")
    @GetMapping(value = "/sequencia", produces = "application/json")
    @Operation(summary = "Gerar sequencia")
    public ResponseEntity<?> obterSequencia(
            @RequestHeader(value = "X-Competencia", required = false) String competencia,
            @RequestHeader(value = "X-Cliente", required = false) String id_cliente) {
        Long ultima_sequencia = Optional
                .ofNullable(objetoRepository.obterSequencial(competencia, Long.valueOf(id_cliente))).orElse(0L);

        Long sq_sequencia = ultima_sequencia + 1;
        String resposta = "%03d".formatted(sq_sequencia);

        return new ResponseEntity<>(Map.of("sequencia", resposta), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR', 'USER')")
    @GetMapping(value = "/sequencia-detalhe/{id}", produces = "application/json")
    @Operation(summary = "Gerar sequencia")
    public ResponseEntity<?> obterSequenciaDetalhe(@PathVariable Long id) {

        Long ultima_sequencia;

        if (id != 0) {
            ultima_sequencia = Optional.ofNullable(itemEmprestimoRepository.obterSequencialById(Long.valueOf(id)))
                    .orElse(0L);

        } else {
            ultima_sequencia = (long) 0;

        }

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

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @DeleteMapping(value = "/deletar/{id}", produces = "application/json")
    public ResponseEntity<?> deletar(@PathVariable Long id) throws Exception {

        service.excluir(id);
        return new ResponseEntity<>(Map.of("message", "Deletado com sucesso!"), HttpStatus.OK);

    }

}
