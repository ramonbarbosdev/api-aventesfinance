package com.api_aventesfinance.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.api_aventesfinance.enums.StatusAtivo;
import com.api_aventesfinance.enums.TipoCategoria;
import com.api_aventesfinance.model.Categoria;
import com.api_aventesfinance.model.CentroCusto;

public class ClienteDTO {

    private Long id_cliente;
    private String nu_cnpjcpf;
    private String nm_cliente;
    private StatusAtivo tp_status;
    private LocalDateTime dt_cadatros;

    public ClienteDTO(Long id_cliente, String nu_cnpjcpf, String nm_cliente, StatusAtivo tp_status,
            LocalDateTime dt_cadatros) {
        this.id_cliente = id_cliente;
        this.nu_cnpjcpf = nu_cnpjcpf;
        this.nm_cliente = nm_cliente;
        this.tp_status = tp_status;
        this.dt_cadatros = dt_cadatros;
    }

    public Long getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Long id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNu_cnpjcpf() {
        return nu_cnpjcpf;
    }

    public void setNu_cnpjcpf(String nu_cnpjcpf) {
        this.nu_cnpjcpf = nu_cnpjcpf;
    }

    public String getNm_cliente() {
        return nm_cliente;
    }

    public void setNm_cliente(String nm_cliente) {
        this.nm_cliente = nm_cliente;
    }

    public StatusAtivo getTp_status() {
        return tp_status;
    }

    public void setTp_status(StatusAtivo tp_status) {
        this.tp_status = tp_status;
    }

    public LocalDateTime getDt_cadatros() {
        return dt_cadatros;
    }

    public void setDt_cadatros(LocalDateTime dt_cadatros) {
        this.dt_cadatros = dt_cadatros;
    }

}