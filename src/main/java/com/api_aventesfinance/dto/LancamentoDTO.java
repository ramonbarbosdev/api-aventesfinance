package com.api_aventesfinance.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.api_aventesfinance.model.ItemLancamento;
import com.api_aventesfinance.model.Lancamento;

public class LancamentoDTO {

    private Long id_lancamento;
    private String cd_lacamento;
    private LocalDateTime dt_lancamento;
    private Long id_centrocusto;
    private Integer dt_ano;
    private String dt_anomes;
    private String ds_lancamento;
    private Double vl_total;
    // private ItemLancamento itemlancamento;

    public LancamentoDTO(Lancamento objeto) {
        this.id_lancamento = objeto.getId_lancemento();
        this.cd_lacamento = objeto.getCd_lancemento();
        this.dt_lancamento = objeto.getDt_lancamento();
        this.id_centrocusto = objeto.getId_centrocusto();
        this.dt_ano = objeto.getDt_ano();
        this.dt_anomes = objeto.getDt_anomes();
        this.ds_lancamento = objeto.getDs_lancemento();
        this.vl_total = objeto.getVl_total();

        // this.itemlancamento = (ItemLancamento) objeto.getItemlancamento();

    }

    // public ItemLancamento getItemlancamento() {
    //     return itemlancamento;
    // }

    // public void setItemlancamento(ItemLancamento itemlancamento) {
    //     this.itemlancamento = itemlancamento;
    // }

    public Long getId_lancamento() {
        return id_lancamento;
    }

    public void setId_lancamento(Long id_lancamento) {
        this.id_lancamento = id_lancamento;
    }

    public String getCd_lacamento() {
        return cd_lacamento;
    }

    public void setCd_lacamento(String cd_lacamento) {
        this.cd_lacamento = cd_lacamento;
    }

    public LocalDateTime getDt_lancamento() {
        return dt_lancamento;
    }

    public void setDt_lancamento(LocalDateTime dt_lancamento) {
        this.dt_lancamento = dt_lancamento;
    }

    public Long getId_centrocusto() {
        return id_centrocusto;
    }

    public void setId_centrocusto(Long id_centrocusto) {
        this.id_centrocusto = id_centrocusto;
    }

    public Integer getDt_ano() {
        return dt_ano;
    }

    public void setDt_ano(Integer dt_ano) {
        this.dt_ano = dt_ano;
    }

    public String getDt_anomes() {
        return dt_anomes;
    }

    public void setDt_anomes(String dt_anomes) {
        this.dt_anomes = dt_anomes;
    }

    public String getDs_lancamento() {
        return ds_lancamento;
    }

    public void setDs_lancamento(String ds_lancamento) {
        this.ds_lancamento = ds_lancamento;
    }

    public Double getVl_total() {
        return vl_total;
    }

    public void setVl_total(Double vl_total) {
        this.vl_total = vl_total;
    }

}
