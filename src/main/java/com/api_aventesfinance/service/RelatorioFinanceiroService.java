package com.api_aventesfinance.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.api_aventesfinance.dto.relatorio.FluxoCaixaDTO;
import com.api_aventesfinance.dto.relatorio.FluxoCaixaDiarioDTO;
import com.api_aventesfinance.dto.relatorio.ReceitaDespesaCategoriaDTO;
import com.api_aventesfinance.dto.relatorio.SituacaoEmprestimoDTO;
import com.api_aventesfinance.repository.RelatorioFinanceiroRepository;

@Service
public class RelatorioFinanceiroService {

    private final RelatorioFinanceiroRepository repository;

    public RelatorioFinanceiroService(RelatorioFinanceiroRepository repository) {
        this.repository = repository;
    }

    public List<FluxoCaixaDTO> obterFluxoCaixaMensal(String competencia, Long id_cliente) {
        return repository.buscarFluxoCaixaMensal(competencia, id_cliente);
    }

    public List<FluxoCaixaDiarioDTO> obterFluxoCaixaDiario(Long id) {
        return repository.buscarFluxoCaixaDiario( id);
    }

    public List<ReceitaDespesaCategoriaDTO> obterReceitaDespesaCategoria(String competencia, Long id_cliente) {
        return repository.buscarReceitaDespesaCategoria( competencia, id_cliente);
    }
    public List<SituacaoEmprestimoDTO> obterSituacaoEmprestimo(String competencia, Long id_cliente) {
        return repository.buscarSituacaoEmprestimo( competencia, id_cliente);
    }

}