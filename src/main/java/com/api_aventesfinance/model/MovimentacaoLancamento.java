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
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "movimentacao_lancamento", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id_lancamento")
})
public class MovimentacaoLancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_movimentacao")
    @SequenceGenerator(name = "seq_movimentacao", sequenceName = "seq_movimentacao", allocationSize = 1)
    @Column(name = "id_movimentacao")
    private Long id_movimentacao;

    @Column(unique = false, nullable = true)
    private Long id_lancamento;

    @ManyToOne()
    @JoinColumn(name = "id_lancamento", insertable = false, updatable = false)
    private Lancamento Lancamento;

    @Column(unique = false, nullable = true)
    private LocalDate dt_movimento;

    @ManyToOne()
    @JoinColumn(name = "id_centrocusto", insertable = false, updatable = false)
    private CentroCusto centroCusto;

    @Column(unique = false, nullable = true)
    private Long id_centrocusto;

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

    public Long getId_lancamento() {
        return id_lancamento;
    }

    public void setId_lancamento(Long id_lancamento) {
        this.id_lancamento = id_lancamento;
    }

    public CentroCusto getCentroCusto() {
        return centroCusto;
    }

    public void setCentroCusto(CentroCusto centroCusto) {
        this.centroCusto = centroCusto;
    }

    public Long getId_centrocusto() {
        return id_centrocusto;
    }

    public void setId_centrocusto(Long id_centrocusto) {
        this.id_centrocusto = id_centrocusto;
    }

    public Lancamento getLancamento() {
        return Lancamento;
    }

    public void setLancamento(Lancamento lancamento) {
        Lancamento = lancamento;
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

}
