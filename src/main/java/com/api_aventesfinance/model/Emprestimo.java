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
@Table(name = "emprestimo")
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_emprestimo")
    @SequenceGenerator(name = "seq_emprestimo", sequenceName = "seq_emprestimo", allocationSize = 1)
    private Long id_emprestimo;

    @NotBlank(message = "O codigo é obrigatorio!")
    @Column(unique = false, nullable = false)
    private String cd_emprestimo;

    @Enumerated(EnumType.STRING)
    private TipoEmprestimo tp_emprestimo;

    @NotBlank(message = "O valor é obrigatorio!")
    @Column(unique = false, nullable = false)
    private Double vl_total;

    @NotBlank(message = "O valor é obrigatorio!")
    @Column(unique = false, nullable = false)
    private LocalDate dt_emprestimo;

    @NotBlank(message = "O status é obrigatorio!")
    @Column(unique = false, nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEmprestimo tp_status;

    @ManyToOne()
    @JoinColumn(name = "id_centrocusto", insertable = false, updatable = false)
    private CentroCusto centroCusto;

    @NotNull(message = "A centro de custo é obrigatória")
    @Column(name = "id_centrocusto")
    private Long id_centrocusto;

    private String ds_observacao;

}
