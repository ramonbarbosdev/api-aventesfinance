package com.api_aventesfinance.dto.relatorio;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FluxoCaixaDiarioDTO(
    Long id_lancamento,
    LocalDate dt_movimento,
    Double receita,
    Double despesa,
    Double saldo
) {}