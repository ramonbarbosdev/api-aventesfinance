package com.api_aventesfinance.model;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "lancamento")
public class Lancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_lancemento;

    @NotBlank(message = "O codigo é obrigatorio!") // para string
    @Column(unique = false, nullable = false)
    private String cd_lancemento;

    @NotNull(message = "A data de lançamento é obrigatória")
    @Column(unique = false, nullable = false)
    private OffsetDateTime  dt_lancamento;

    @ManyToOne()
    @JoinColumn(name = "id_centrocusto", insertable = false, updatable = false)
    private CentroCusto centroCusto;

    @NotNull(message = "A centro de custo é obrigatória")  
    @Column(name = "id_centrocusto")
    private Long id_centrocusto;

    @Column(unique = false, nullable = true)
    private Double vl_total;

    @NotBlank(message = "O Data ano-mês é obrigatorio!")
    @Column(unique = false, nullable = false)
    private String dt_anomes;

    @Column(unique = false, nullable = true)
    private String ds_lancemento;

    //Listagem de itens
    @OneToMany(mappedBy = "id_lancamento", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    public List<ItemLancamento> itemlancamento = new ArrayList<ItemLancamento>();

    public List<ItemLancamento> getItemlancamento() {
        return itemlancamento != null ? itemlancamento : Collections.emptyList();
    }

    public void setItemlancamento(List<ItemLancamento> itemlancamento) {
        this.itemlancamento = itemlancamento;
    }

    public Long getId_lancemento() {
        return id_lancemento;
    }

    public void setId_lancemento(Long id_lancemento) {
        this.id_lancemento = id_lancemento;
    }

    public String getCd_lancemento() {
        return cd_lancemento;
    }

    public void setCd_lancemento(String cd_lancemento) {
        this.cd_lancemento = cd_lancemento;
    }

    public Long getId_centrocusto() {
        return id_centrocusto;
    }

    public void setId_centrocusto(Long id_centrocusto) {
        this.id_centrocusto = id_centrocusto;
    }

    public Double getVl_total() {
        return vl_total;
    }

    public void setVl_total(Double vl_total) {
        this.vl_total = vl_total;
    }

    public OffsetDateTime   getDt_lancamento() {
        return dt_lancamento;
    }

    public void setDt_lancamento(OffsetDateTime  dt_lancamento) {
        this.dt_lancamento = dt_lancamento;
    }


    public String getDt_anomes() {
        return dt_anomes;
    }

    public void setDt_anomes(String dt_anomes) {
        this.dt_anomes = dt_anomes;
    }

    public String getDs_lancemento() {
        return ds_lancemento;
    }

    public void setDs_lancemento(String ds_lancemento) {
        this.ds_lancemento = ds_lancemento;
    }

}
