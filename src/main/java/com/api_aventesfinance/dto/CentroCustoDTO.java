package com.api_aventesfinance.dto;

import java.io.Serializable;

import com.api_aventesfinance.enums.TipoCategoria;
import com.api_aventesfinance.model.Categoria;
import com.api_aventesfinance.model.CentroCusto;

public class CentroCustoDTO {

    private Long id_categoria;
    private String cd_centrocusto;
    private String nm_centrocusto;
    private String ds_centrocusto;

    public CentroCustoDTO() {
    } // Construtor necessário para serialização

    public CentroCustoDTO(CentroCusto objeto) {
        this.id_categoria = objeto.getId_centrocusto();
        this.cd_centrocusto = objeto.getCd_centrocusto();
        this.nm_centrocusto = objeto.getNm_centrocusto();
        this.ds_centrocusto = objeto.getDs_centrocusto();
    }

    public Long getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(Long id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getCd_centrocusto() {
        return cd_centrocusto;
    }

    public void setCd_centrocusto(String cd_centrocusto) {
        this.cd_centrocusto = cd_centrocusto;
    }

    public String getNm_centrocusto() {
        return nm_centrocusto;
    }

    public void setNm_centrocusto(String nm_centrocusto) {
        this.nm_centrocusto = nm_centrocusto;
    }

    public String getDs_centrocusto() {
        return ds_centrocusto;
    }

    public void setDs_centrocusto(String ds_centrocusto) {
        this.ds_centrocusto = ds_centrocusto;
    }
}