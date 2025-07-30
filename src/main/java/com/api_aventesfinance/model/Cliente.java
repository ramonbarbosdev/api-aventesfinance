package com.api_aventesfinance.model;

import java.time.LocalDateTime;

import com.api_aventesfinance.enums.StatusAtivo;
import com.api_aventesfinance.enums.TipoCategoria;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cliente")
    @SequenceGenerator(name = "seq_cliente", sequenceName = "seq_cliente", allocationSize = 1)
    private Long id_cliente;

    @NotBlank(message = "O CPF/CNPJ é obrigatorio!")
    @Column(unique = false, nullable = false)
    private String nu_cnpjcpf;

    @NotBlank(message = "O Nome é obrigatorio!")
    @Column(unique = false, nullable = false)
    private String nm_cliente;

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
