package com.api_aventesfinance.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.api_aventesfinance.enums.StatusEmprestimo;
import com.api_aventesfinance.enums.TipoCategoria;
import com.api_aventesfinance.enums.TipoEmprestimo;
import com.api_aventesfinance.model.Categoria;
import com.api_aventesfinance.model.CentroCusto;
import com.api_aventesfinance.model.Emprestimo;

public class EmprestimoDTO {

    private Long id_emprestimo ;
    private String cd_emprestimo ;
    private TipoEmprestimo tp_emprestimo;
    private Double vl_total;
    private LocalDate dt_emprestimo;
    private StatusEmprestimo tp_status;
    private Long id_centrocusto;
    private String ds_observacao;

    public EmprestimoDTO(Long id_emprestimo, String cd_emprestimo, TipoEmprestimo tp_emprestimo, Double vl_total,
            LocalDate dt_emprestimo, StatusEmprestimo tp_status, Long id_centrocusto, String ds_observacao) {
        this.id_emprestimo = id_emprestimo;
        this.cd_emprestimo = cd_emprestimo;
        this.tp_emprestimo = tp_emprestimo;
        this.vl_total = vl_total;
        this.dt_emprestimo = dt_emprestimo;
        this.tp_status = tp_status;
        this.id_centrocusto = id_centrocusto;
        this.ds_observacao = ds_observacao;
    }

    public EmprestimoDTO() {
    } // Construtor necessário para serialização

    public Long getId_emprestimo() {
        return id_emprestimo;
    }

    public void setId_emprestimo(Long id_emprestimo) {
        this.id_emprestimo = id_emprestimo;
    }

    public String getCd_emprestimo() {
        return cd_emprestimo;
    }

    public void setCd_emprestimo(String cd_emprestimo) {
        this.cd_emprestimo = cd_emprestimo;
    }

    public TipoEmprestimo getTp_emprestimo() {
        return tp_emprestimo;
    }

    public void setTp_emprestimo(TipoEmprestimo tp_emprestimo) {
        this.tp_emprestimo = tp_emprestimo;
    }

    public Double getVl_total() {
        return vl_total;
    }

    public void setVl_total(Double vl_total) {
        this.vl_total = vl_total;
    }

    public LocalDate getDt_emprestimo() {
        return dt_emprestimo;
    }

    public void setDt_emprestimo(LocalDate dt_emprestimo) {
        this.dt_emprestimo = dt_emprestimo;
    }

    public StatusEmprestimo getTp_status() {
        return tp_status;
    }

    public void setTp_status(StatusEmprestimo tp_status) {
        this.tp_status = tp_status;
    }

    public Long getId_centrocusto() {
        return id_centrocusto;
    }

    public void setId_centrocusto(Long id_centrocusto) {
        this.id_centrocusto = id_centrocusto;
    }

    public String getDs_observacao() {
        return ds_observacao;
    }

    public void setDs_observacao(String ds_observacao) {
        this.ds_observacao = ds_observacao;
    }

  
}