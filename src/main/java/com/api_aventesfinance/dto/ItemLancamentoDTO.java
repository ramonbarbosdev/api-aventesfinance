package com.api_aventesfinance.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;

import com.api_aventesfinance.model.ItemLancamento;
import com.api_aventesfinance.model.Lancamento;

public class ItemLancamentoDTO {

    private Long id_itemlancamento;
    private Long id_lancamento;
    private String cd_itemlancamento;
    private Long id_categoria;
    private Double vl_itemlancamento;

    public ItemLancamentoDTO() {  }

    public String getCd_itemlancamento() {
        return cd_itemlancamento;
    }

    public void setCd_itemlancamento(String cd_itemlancamento) {
        this.cd_itemlancamento = cd_itemlancamento;
    }

    public Long getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(Long id_categoria) {
        this.id_categoria = id_categoria;
    }

    public Long getId_itemlancamento() {
        return id_itemlancamento;
    }

    public void setId_itemlancamento(Long id_itemlancamento) {
        this.id_itemlancamento = id_itemlancamento;
    }

    public Long getId_lancamento() {
        return id_lancamento;
    }

    public void setId_lancamento(Long id_lancamento) {
        this.id_lancamento = id_lancamento;
    }

    public Double getVl_itemlancamento() {
        return vl_itemlancamento;
    }

    public void setVl_itemlancamento(Double vl_itemlancamento) {
        this.vl_itemlancamento = vl_itemlancamento;
    }

}
