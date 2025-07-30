package com.api_aventesfinance.model;

import java.time.LocalDateTime;

import com.api_aventesfinance.enums.StatusAtivo;
import com.api_aventesfinance.enums.TipoCategoria;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "usuario_cliente")
public class UsuarioCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario_cliente")
    @SequenceGenerator(name = "seq_usuario_cliente", sequenceName = "seq_usuario_cliente", allocationSize = 1)
    private Long id_usuariocliente;

    // @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
    private Usuario usuario;

    @NotNull(message = "O Usuario é obrigatória")
    @Column(name = "id_usuario")
    private Long id_usuario;

    // @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "id_cliente", insertable = false, updatable = false)
    private Cliente cliente;

    @NotNull(message = "O Cliente é obrigatória")
    @Column(name = "id_cliente")
    private Long id_cliente;

    @Schema(enumAsRef = true, description = "Tipo do empréstimo (ATIVO, INATIVO)")
    @NotNull(message = "O tipo de status  é obrigatorio!")
    @Enumerated(EnumType.STRING)
    private StatusAtivo tp_status;

    @Column(name = "dt_cadatros", nullable = false, updatable = false)
    private LocalDateTime dt_cadatros;

    @PrePersist
    protected void onCreate() {
        this.dt_cadatros = LocalDateTime.now();
    }

    public Long getId_usuariocliente() {
        return id_usuariocliente;
    }

    public void setId_usuariocliente(Long id_usuariocliente) {
        this.id_usuariocliente = id_usuariocliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

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
