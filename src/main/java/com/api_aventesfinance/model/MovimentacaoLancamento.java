package com.api_aventesfinance.model;

import java.time.LocalDate;

import com.api_aventesfinance.enums.TipoCategoria;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "movimentacao_lancamento", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id_lancamento")
})
public class MovimentacaoLancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimentacao")
    private Long id_movimentacao;

    @ManyToOne
    @JoinColumn(name = "id_lancamento", nullable = true)
    private Lancamento lancamento;

    @Column(unique = false, nullable = true)
    private LocalDate dt_movimento;

    @ManyToOne
    @JoinColumn(name = "id_centrocusto", nullable = true)
    private CentroCusto centroCusto;

    @Column(unique = false, nullable = true)
    private Double vl_receita;

    @Column(unique = false, nullable = true)
    private Double vl_despesa;

    @Column(unique = false, nullable = true)
    private Double vl_saldo;

    @Column(unique = false, nullable = true)
    private String dt_anomes;

    public Long getId_movimentacao() {
        return id_movimentacao;
    }

    public void setId_movimentacao(Long id_movimentacao) {
        this.id_movimentacao = id_movimentacao;
    }

 

    public CentroCusto getCentroCusto() {
        return centroCusto;
    }

    public void setCentroCusto(CentroCusto centroCusto) {
        this.centroCusto = centroCusto;
    }


  

    public Double getVl_despesa() {
        return vl_despesa;
    }

    public void setVl_despesa(Double vl_despesa) {
        this.vl_despesa = vl_despesa;
    }

    public Double getVl_receita() {
        return vl_receita;
    }

    public void setVl_receita(Double vl_receita) {
        this.vl_receita = vl_receita;
    }

    public Double getVl_saldo() {
        return vl_saldo;
    }

    public void setVl_saldo(Double vl_saldo) {
        this.vl_saldo = vl_saldo;
    }

    public String getDt_anomes() {
        return dt_anomes;
    }

    public void setDt_anomes(String dt_anomes) {
        this.dt_anomes = dt_anomes;
    }

    public LocalDate getDt_movimento() {
        return dt_movimento;
    }

    public void setDt_movimento(LocalDate dt_movimento) {
        this.dt_movimento = dt_movimento;
    }

    public Lancamento getLancamento() {
        return lancamento;
    }
    public void setLancamento(Lancamento lancamento) {
        this.lancamento = lancamento;
    }
    

}
