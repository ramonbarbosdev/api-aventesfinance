package com.api_aventesfinance.model;

import java.time.LocalDate;


import io.swagger.v3.oas.annotations.media.Schema;
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

@Entity
@Table(name = "movimentacao_caixa")
public class MovimentacaoCaixa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_movimentacaocaixa")
    @SequenceGenerator(name = "seq_movimentacaocaixa", sequenceName = "seq_movimentacaocaixa", allocationSize = 1)
    @Column(name = "id_movimentacaocaixa")
    private Long id_movimentacao;

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
    private Double vl_emprestimo;

    @Column(unique = false, nullable = true)
    private Double vl_saldo;

    @Column(unique = false, nullable = true)
    private String dt_anomes;

    @ManyToOne()
    @JoinColumn(name = "id_cliente", insertable = false, updatable = false)
    private Cliente cliente;

    @Column(name = "id_cliente")
    private Long id_cliente;

    public Long getId_movimentacao() {
        return id_movimentacao;
    }

    public void setId_movimentacao(Long id_movimentacao) {
        this.id_movimentacao = id_movimentacao;
    }

    public LocalDate getDt_movimento() {
        return dt_movimento;
    }

    public void setDt_movimento(LocalDate dt_movimento) {
        this.dt_movimento = dt_movimento;
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

    public Double getVl_receita() {
        return vl_receita;
    }

    public void setVl_receita(Double vl_receita) {
        this.vl_receita = vl_receita;
    }

    public Double getVl_despesa() {
        return vl_despesa;
    }

    public void setVl_despesa(Double vl_despesa) {
        this.vl_despesa = vl_despesa;
    }

    public Double getVl_emprestimo() {
        return vl_emprestimo;
    }

    public void setVl_emprestimo(Double vl_emprestimo) {
        this.vl_emprestimo = vl_emprestimo;
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Long getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Long id_cliente) {
        this.id_cliente = id_cliente;
    }


    

}
