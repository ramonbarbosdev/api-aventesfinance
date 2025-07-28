package com.api_aventesfinance.dto.relatorio;


public record ReceitaDespesaCategoriaDTO(
    String nm_categoria,
    String tp_categoria,
    Double total
) {}