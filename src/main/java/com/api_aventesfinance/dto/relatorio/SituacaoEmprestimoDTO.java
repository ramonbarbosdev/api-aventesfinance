package com.api_aventesfinance.dto.relatorio;

import java.time.LocalDate;

import com.api_aventesfinance.enums.StatusEmprestimo;
import com.api_aventesfinance.enums.TipoEmprestimo;

public record SituacaoEmprestimoDTO(
    Long id_emprestimo,
    String cd_emprestimo,
    String tp_emprestimo,
    LocalDate dt_emprestimo,
    String tp_status,
    Double total_emprestado,
    Double total_pago,
    Double em_aberto
) {}