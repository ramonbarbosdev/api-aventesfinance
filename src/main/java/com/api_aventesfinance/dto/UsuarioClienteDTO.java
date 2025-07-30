package com.api_aventesfinance.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.api_aventesfinance.enums.StatusAtivo;
import com.api_aventesfinance.enums.TipoCategoria;
import com.api_aventesfinance.model.Categoria;
import com.api_aventesfinance.model.CentroCusto;
import com.api_aventesfinance.model.Usuario;
import com.api_aventesfinance.model.UsuarioCliente;

public class UsuarioClienteDTO {
    private Long id_usuariocliente;
    private Long id_usuario;
    private Long id_cliente;
    private StatusAtivo tp_status;
    private LocalDateTime dt_cadatros;
    public UsuarioClienteDTO(Long id_usuariocliente, Long id_usuario, Long id_cliente, StatusAtivo tp_status,
            LocalDateTime dt_cadatros) {
        this.id_usuariocliente = id_usuariocliente;
        this.id_usuario = id_usuario;
        this.id_cliente = id_cliente;
        this.tp_status = tp_status;
        this.dt_cadatros = dt_cadatros;
    }
    public Long getId_usuariocliente() {
        return id_usuariocliente;
    }
    public void setId_usuariocliente(Long id_usuariocliente) {
        this.id_usuariocliente = id_usuariocliente;
    }
    public Long getId_usuario() {
        return id_usuario;
    }
    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }
    public Long getId_cliente() {
        return id_cliente;
    }
    public void setId_cliente(Long id_cliente) {
        this.id_cliente = id_cliente;
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