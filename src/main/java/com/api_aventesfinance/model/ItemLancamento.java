package com.api_aventesfinance.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "item_lancamento")
public class ItemLancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_itemlancamento;

    @ManyToOne()
    @JoinColumn(name = "id_lancamento", insertable = false, updatable = false)
    private Lancamento Lancamento;

    @NotBlank(message = "O Lancamento é obrigatorio!")
    @Column(name = "id_lancamento")
    private Long id_lancamento;

    @NotBlank(message = "O codigo é obrigatorio!")
    @Column(unique = false, nullable = false)
    private String cd_itemlancamento;

    @ManyToOne()
    @JoinColumn(name = "id_categoria", insertable = false, updatable = false)
    private Categoria categoria;

    @NotBlank(message = "O Categoria é obrigatorio!")
    @Column(name = "id_categoria")
    private Long id_categoria;

    @NotBlank(message = "O valor é obrigatorio!")
    @Column(unique = false, nullable = false)
    private Double vl_itemlancamento;

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

    public Double getVl_itemlancamento() {
        return vl_itemlancamento;
    }

    public void setVl_itemlancamento(Double vl_itemlancamento) {
        this.vl_itemlancamento = vl_itemlancamento;
    }

}
