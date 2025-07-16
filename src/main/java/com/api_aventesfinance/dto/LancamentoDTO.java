package com.api_aventesfinance.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.List;

import com.api_aventesfinance.model.ItemLancamento;
import com.api_aventesfinance.model.Lancamento;
import com.fasterxml.jackson.annotation.JsonFormat;

public class LancamentoDTO {

    private Long id_lancamento;
    private String cd_lancamento;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private LocalDateTime dt_lancamento;
    private Long id_centrocusto;
    private String dt_anomes;
    private String ds_lancamento;
    private Double vl_total;
    private List<ItemLancamentoDTO> itens;

    public LancamentoDTO() {}

   
    public List<ItemLancamentoDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemLancamentoDTO> itens) {
        this.itens = itens;
    }

    public Long getId_lancamento() {
        return id_lancamento;
    }

    public void setId_lancamento(Long id_lancamento) {
        this.id_lancamento = id_lancamento;
    }

 
    public String getCd_lancamento() {
        return cd_lancamento;
    }
    public void setCd_lancamento(String cd_lancamento) {
        this.cd_lancamento = cd_lancamento;
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
