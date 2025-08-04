package com.api_aventesfinance.model;

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
@Table(name = "centro_custo")
public class CentroCusto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_centrocusto;

    @NotBlank(message = "O codigo é obrigatorio!")
    @Column(unique = false, nullable = false)
    private String cd_centrocusto;

    @NotBlank(message = "O nome é obrigatorio!")
    @Column(unique = false, nullable = false)
    private String nm_centrocusto;

    @NotBlank(message = "O descrição é obrigatorio!")
    @Column(unique = false, nullable = true)
    private String ds_centrocusto;

       @ManyToOne()
    @JoinColumn(name = "id_cliente", insertable = false, updatable = false)
    private Cliente cliente;

    @Column(name = "id_cliente")
    private Long id_cliente;

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
    

    public Long getId_centrocusto() {
        return id_centrocusto;
    }

    public void setId_centrocusto(Long id_centrocusto) {
        this.id_centrocusto = id_centrocusto;
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
