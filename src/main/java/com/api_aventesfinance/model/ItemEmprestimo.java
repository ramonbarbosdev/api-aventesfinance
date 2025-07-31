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


    @NotNull(message = "O Emprestimo obrigatorio")
    @Column(name = "id_emprestimo")
    private Long id_emprestimo;


    @NotNull(message = "O valor é obrigatorio!")
    @Column(unique = false, nullable = false)
    private Double vl_emprestimo;

    @NotNull(message = "O Data do vencimento é obrigatorio!")
    @Column(unique = false, nullable = false)
    private LocalDate dt_vencimento;

    @NotNull(message = "O Data do pagamento é obrigatorio!")
    @Column(unique = false, nullable = false)
    private LocalDate dt_pagamento ;

    @NotNull(message = "O status é obrigatorio!")
    @Column(unique = false, nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEmprestimo tp_itemstatus;

    @Column(name = "ds_anotacao")
    private String ds_anotacao;

    public String getDs_anotacao() {
        return ds_anotacao;
    }
    public void setDs_anotacao(String ds_anotacao) {
        this.ds_anotacao = ds_anotacao;
    }
    

    public Long getId_itememprestimo() {
        return id_itememprestimo;
    }

    public void setId_itememprestimo(Long id_itememprestimo) {
        this.id_itememprestimo = id_itememprestimo;
    }

    public String getCd_itememprestimo() {
        return cd_itememprestimo;
    }

    public void setCd_itememprestimo(String cd_itememprestimo) {
        this.cd_itememprestimo = cd_itememprestimo;
    }

  

    public Long getId_emprestimo() {
        return id_emprestimo;
    }

    public void setId_emprestimo(Long id_emprestimo) {
        this.id_emprestimo = id_emprestimo;
    }

    public Double getVl_emprestimo() {
        return vl_emprestimo;
    }

    public void setVl_emprestimo(Double vl_emprestimo) {
        this.vl_emprestimo = vl_emprestimo;
    }

    public LocalDate getDt_vencimento() {
        return dt_vencimento;
    }

    public void setDt_vencimento(LocalDate dt_vencimento) {
        this.dt_vencimento = dt_vencimento;
    }

    public LocalDate getDt_pagamento() {
        return dt_pagamento;
    }

    public void setDt_pagamento(LocalDate dt_pagamento) {
        this.dt_pagamento = dt_pagamento;
    }

    public StatusEmprestimo getTp_itemstatus() {
        return tp_itemstatus;
    }

    public void setTp_itemstatus(StatusEmprestimo tp_itemstatus) {
        this.tp_itemstatus = tp_itemstatus;
    }


}
