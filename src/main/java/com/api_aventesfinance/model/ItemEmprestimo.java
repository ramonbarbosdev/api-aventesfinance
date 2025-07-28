package com.api_aventesfinance.model;

import java.time.LocalDate;

import com.api_aventesfinance.enums.StatusEmprestimo;
import com.api_aventesfinance.enums.TipoCategoria;
import com.api_aventesfinance.enums.TipoEmprestimo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "item_emprestimo")
public class ItemEmprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_itememprestimo")
    @SequenceGenerator(name = "seq_itememprestimo", sequenceName = "seq_itememprestimo", allocationSize = 1)
    private Long id_itememprestimo;

    @NotBlank(message = "O codigo é obrigatorio!")
    @Column(unique = false, nullable = false)
    private String cd_itememprestimo;

    @ManyToOne()
    @JoinColumn(name = "id_emprestimo", insertable = false, updatable = false)
    private Emprestimo emprestimo;

    @NotNull(message = "O emprestimo obrigatória")
    @Column(name = "id_emprestimo")
    private Long id_emprestimo;


    @NotBlank(message = "O valor é obrigatorio!")
    @Column(unique = false, nullable = false)
    private Double vl_emprestimo;

    @NotBlank(message = "O Data do vencimento é obrigatorio!")
    @Column(unique = false, nullable = false)
    private LocalDate dt_vencimento;

    @NotBlank(message = "O Data do pagamento é obrigatorio!")
    @Column(unique = false, nullable = false)
    private LocalDate dt_pagamento ;

    @NotBlank(message = "O status é obrigatorio!")
    @Column(unique = false, nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEmprestimo tp_itemstatus;


}
