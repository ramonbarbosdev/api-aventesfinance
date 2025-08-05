package com.api_aventesfinance.dto;

import java.time.LocalDate;


public record MovimentacaoCaixaDTO(
    LocalDate dt_movimento,
    String dt_anomes,
    Long id_centrocusto,
    Long id_lancamento,
    Long id_cliente,
    Double vl_receita,
    Double vl_despesa,
    Double vl_emprestimo,
    Double vl_saldo
) {}