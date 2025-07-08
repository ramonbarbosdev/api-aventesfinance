package com.api_aventesfinance.model;

import com.api_aventesfinance.model.enums.TipoCategoria;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "categoria")
public class Categoria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_categoria;

    
    @NotBlank(message = "O codigo é obrigatorio!")
    @Column(unique = false, nullable = false)
    private String cd_categoria;

    @NotBlank(message = "O nome é obrigatorio!")
    @Column(unique = false, nullable = false)
    private String nm_categoria;

    @Enumerated(EnumType.STRING)
    private TipoCategoria tp_categoria;

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
