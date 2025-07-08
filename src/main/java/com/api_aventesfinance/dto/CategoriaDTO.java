package com.api_aventesfinance.dto;

import java.io.Serializable;

import com.api_aventesfinance.model.Categoria;
import com.api_aventesfinance.model.enums.TipoCategoria;

public class CategoriaDTO implements Serializable {

    private Long id_categoria;
    private String cd_categoria;
    private String nm_categoria;
    private TipoCategoria tp_categoria;

    public CategoriaDTO() {
    } // Construtor necessário para serialização

    public CategoriaDTO(Categoria categoria) {
        this.id_categoria = categoria.getId_categoria();
        this.cd_categoria = categoria.getCd_categoria();
        this.nm_categoria = categoria.getNm_categoria();
        this.tp_categoria = categoria.getTp_categoria();
    }

    public Long getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(Long id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getCd_categoria() {
        return cd_categoria;
    }

    public void setCd_categoria(String cd_categoria) {
        this.cd_categoria = cd_categoria;
    }

    public String getNm_categoria() {
        return nm_categoria;
    }

    public void setNm_categoria(String nm_categoria) {
        this.nm_categoria = nm_categoria;
    }

    public TipoCategoria getTp_categoria() {
        return tp_categoria;
    }

    public void setTp_categoria(TipoCategoria tp_categoria) {
        this.tp_categoria = tp_categoria;
    }
}