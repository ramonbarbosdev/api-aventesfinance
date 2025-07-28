package com.api_aventesfinance.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.api_aventesfinance.dto.relatorio.FluxoCaixaDTO;
import com.api_aventesfinance.dto.relatorio.FluxoCaixaDiarioDTO;
import com.api_aventesfinance.dto.relatorio.ReceitaDespesaCategoriaDTO;
import com.api_aventesfinance.repository.RelatorioFinanceiroRepository;

@Service
public class RelatorioFinanceiroService {

    private final RelatorioFinanceiroRepository repository;

    public RelatorioFinanceiroService(RelatorioFinanceiroRepository repository) {
        this.repository = repository;
    }

    public List<FluxoCaixaDTO> obterFluxoCaixaMensal() {
        return repository.buscarFluxoCaixaMensal();
    }

    public List<FluxoCaixaDiarioDTO> obterFluxoCaixaDiario(Long id) {
        return repository.buscarFluxoCaixaDiario( id);
    }

    public List<ReceitaDespesaCategoriaDTO> obterReceitaDespesaCategoria() {
        return repository.buscarReceitaDespesaCategoria();
    }

}