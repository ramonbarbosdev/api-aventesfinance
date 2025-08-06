package com.api_aventesfinance.dto.relatorio;

import java.time.LocalDate;

import org.springframework.cglib.core.Local;

import com.api_aventesfinance.enums.StatusEmprestimo;
import com.api_aventesfinance.enums.TipoEmprestimo;

public record EvolucaoReceitaDespesaDTO(
    Long id_centrocusto,
    LocalDate dt_movimento,
    Long id_cliente,
    Double vl_receita,
    Double vl_despesa
) {}