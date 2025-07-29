package com.api_aventesfinance.model;

import java.time.LocalDate;
import java.time.LocalDateTime;


import com.api_aventesfinance.enums.StatusCompetencia;

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

@Entity
@Table(name = "competencia")
public class Competencia {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_competencia")
    @SequenceGenerator(name = "seq_competencia", sequenceName = "seq_competencia", allocationSize = 1)
    private Long id_competencia;

    @Column(name = "cd_competencia", unique = true, nullable = false, length = 6)
    private String cd_competencia;

    @Column(name = "dt_inicio", nullable = false)
    private LocalDate dt_inicio;

    @Column(name = "dt_fim", nullable = false)
    private LocalDate dt_fim;

    @Enumerated(EnumType.STRING)
    @Column(name = "tp_status", nullable = false)
    private StatusCompetencia tp_status;

    @Column(name = "dt_cadatros", nullable = false, updatable = false)
    private LocalDateTime dt_cadatros;

    @PrePersist
    protected void onCreate() {
        this.dt_cadatros = LocalDateTime.now();
    }

    public Long getId_competencia() {
        return id_competencia;
    }

    public void setId_competencia(Long id_competencia) {
        this.id_competencia = id_competencia;
    }

    public String getCd_competencia() {
        return cd_competencia;
    }

    public void setCd_competencia(String cd_competencia) {
        this.cd_competencia = cd_competencia;
    }

    public LocalDate getDt_inicio() {
        return dt_inicio;
    }

    public void setDt_inicio(LocalDate dt_inicio) {
        this.dt_inicio = dt_inicio;
    }

    public LocalDate getDt_fim() {
        return dt_fim;
    }

    public void setDt_fim(LocalDate dt_fim) {
        this.dt_fim = dt_fim;
    }

    public StatusCompetencia getTp_status() {
        return tp_status;
    }

    public void setTp_status(StatusCompetencia tp_status) {
        this.tp_status = tp_status;
    }

    public LocalDateTime getDt_cadatros() {
        return dt_cadatros;
    }

    public void setDt_cadatros(LocalDateTime dt_cadatros) {
        this.dt_cadatros = dt_cadatros;
    }

    

}
