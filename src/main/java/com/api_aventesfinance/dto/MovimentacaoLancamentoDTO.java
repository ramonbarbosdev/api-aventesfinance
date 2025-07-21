package com.api_aventesfinance.dto;

import java.time.LocalDate;

public class MovimentacaoLancamentoDTO {
    
    private Long idLancamento;
    private LocalDate dtMovimento;
    private Long idCentroCusto;
    private Double vlReceita;
    private Double vlDespesa;
    private Double vlSaldo;
    private String dtAnomes;

    public MovimentacaoLancamentoDTO(Long idLancamento, LocalDate dtMovimento, Long idCentroCusto,
                                     Double vlReceita, Double vlDespesa, Double vlSaldo, String dtAnomes) {
        this.idLancamento = idLancamento;
        this.dtMovimento = dtMovimento;
        this.idCentroCusto = idCentroCusto;
        this.vlReceita = vlReceita;
        this.vlDespesa = vlDespesa;
        this.vlSaldo = vlSaldo;
        this.dtAnomes = dtAnomes;
    }
    
    public String getDtAnomes() {
        return dtAnomes;
    }
    public void setDtAnomes(String dtAnomes) {
        this.dtAnomes = dtAnomes;
    }
    public LocalDate getDtMovimento() {
        return dtMovimento;
    }
    public void setDtMovimento(LocalDate dtMovimento) {
        this.dtMovimento = dtMovimento;
    }
    public Long getIdCentroCusto() {
        return idCentroCusto;
    }
    public void setIdCentroCusto(Long idCentroCusto) {
        this.idCentroCusto = idCentroCusto;
    }
    public Long getIdLancamento() {
        return idLancamento;
    }public void setIdLancamento(Long idLancamento) {
        this.idLancamento = idLancamento;
    }
    public Double getVlDespesa() {
        return vlDespesa;
    }
    public void setVlDespesa(Double vlDespesa) {
        this.vlDespesa = vlDespesa;
    }
    public Double getVlReceita() {
        return vlReceita;
    }
    public void setVlReceita(Double vlReceita) {
        this.vlReceita = vlReceita;
    }
    public Double getVlSaldo() {
        return vlSaldo;
    }
    public void setVlSaldo(Double vlSaldo) {
        this.vlSaldo = vlSaldo;
    }

}
