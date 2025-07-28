package com.api_aventesfinance.dto.relatorio;

import java.math.BigDecimal;

public record FluxoCaixaDTO(
    String mesAno,
    Long id_centrocusto,
    String nm_centrocusto,
    BigDecimal receita,
    BigDecimal despesa,
    BigDecimal saldo
) {}