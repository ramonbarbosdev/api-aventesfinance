package com.api_aventesfinance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api_aventesfinance.dto.relatorio.FluxoCaixaDTO;
import com.api_aventesfinance.dto.relatorio.FluxoCaixaDiarioDTO;
import com.api_aventesfinance.dto.relatorio.ReceitaDespesaCategoriaDTO;
import com.api_aventesfinance.dto.relatorio.SituacaoEmprestimoDTO;
import com.api_aventesfinance.service.RelatorioFinanceiroService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/relatorios")
@Tag(name = "Relatorios")
public class RelatorioFinanceiroController {
    

    @Autowired
    private RelatorioFinanceiroService service;

    @GetMapping(value = "/fluxo-caixa-mensal", produces = "application/json")
    public ResponseEntity<List<?>> obterFluxoCaixaMensal(@RequestHeader(value = "X-Competencia", required = false) String competencia) {

        List<FluxoCaixaDTO> resultado = service.obterFluxoCaixaMensal(competencia);

        return new ResponseEntity<>(resultado, HttpStatus.CREATED);
    }
    @GetMapping(value = "/fluxo-caixa-diario/{id}", produces = "application/json")
    public ResponseEntity<List<?>> obterFluxoCaixaDiario(@PathVariable Long id) {

        List<FluxoCaixaDiarioDTO> resultado = service.obterFluxoCaixaDiario(id);

        return new ResponseEntity<>(resultado, HttpStatus.CREATED);
    }

    @GetMapping(value = "/fluxo-categoria", produces = "application/json")
    public ResponseEntity<List<?>> obterReceitaDespesaCategoria(@RequestHeader(value = "X-Competencia", required = false) String competencia) {

       List<ReceitaDespesaCategoriaDTO> resultado = service.obterReceitaDespesaCategoria(competencia);

        return new ResponseEntity<>(resultado, HttpStatus.CREATED);
    }

    @GetMapping(value = "/situacao-emprestimo", produces = "application/json")
    public ResponseEntity<List<?>> obterSituacaoEmprestimo(@RequestHeader(value = "X-Competencia", required = false) String competencia) {

       List<SituacaoEmprestimoDTO> resultado = service.obterSituacaoEmprestimo(competencia);

        return new ResponseEntity<>(resultado, HttpStatus.CREATED);
    }
}
