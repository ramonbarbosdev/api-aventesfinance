package com.api_aventesfinance.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.api_aventesfinance.enums.StatusEmprestimo;
import com.api_aventesfinance.enums.TipoCategoria;
import com.api_aventesfinance.enums.TipoEmprestimo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "emprestimo")
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_emprestimo")
    @SequenceGenerator(name = "seq_emprestimo", sequenceName = "seq_emprestimo", allocationSize = 1)
    private Long id_emprestimo;

    @NotBlank(message = "O codigo é obrigatorio!")
    @Column(unique = false, nullable = false)
    private String cd_emprestimo;

    @Schema(enumAsRef = true, description = "Tipo do empréstimo (RECEBIDO, CONCEDIDO)")
    @NotNull(message = "O tipo emprestimo é obrigatorio!")
    @Column(unique = false, nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoEmprestimo tp_emprestimo;

    private Double vl_total;

    @NotNull(message = "O valor é obrigatorio!")
    @Column(unique = false, nullable = false)
    private LocalDate dt_emprestimo;

    @Schema(enumAsRef = true, description = "Status do empréstimo (PENDENTE, QUITADO, ATRASADO)")
    @NotNull(message = "O status é obrigatorio!")
    @Column(unique = false, nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEmprestimo tp_status;

    @ManyToOne()
    @JoinColumn(name = "id_centrocusto", insertable = false, updatable = false)
    private CentroCusto centroCusto;

    @Column(name = "id_centrocusto")
    private Long id_centrocusto;

    private String ds_observacao;

     // Listagem de itens
    @OneToMany(mappedBy = "id_emprestimo", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    public List<ItemEmprestimo> itens = new ArrayList<ItemEmprestimo>();

    public List<ItemEmprestimo> getItens() {
        return itens;
    }

    public void setItens(List<ItemEmprestimo> itens) {
        this.itens = itens;
    }

    public Long getId_emprestimo() {
        return id_emprestimo;
    }

    public void setId_emprestimo(Long id_emprestimo) {
        this.id_emprestimo = id_emprestimo;
    }

    public String getCd_emprestimo() {
        return cd_emprestimo;
    }

    public void setCd_emprestimo(String cd_emprestimo) {
        this.cd_emprestimo = cd_emprestimo;
    }

    public TipoEmprestimo getTp_emprestimo() {
        return tp_emprestimo;
    }

    public void setTp_emprestimo(TipoEmprestimo tp_emprestimo) {
        this.tp_emprestimo = tp_emprestimo;
    }

    public Double getVl_total() {
        return vl_total;
    }

    public void setVl_total(Double vl_total) {
        this.vl_total = vl_total;
    }

    public LocalDate getDt_emprestimo() {
        return dt_emprestimo;
    }

    public void setDt_emprestimo(LocalDate dt_emprestimo) {
        this.dt_emprestimo = dt_emprestimo;
    }

    public StatusEmprestimo getTp_status() {
        return tp_status;
    }

    public void setTp_status(StatusEmprestimo tp_status) {
        this.tp_status = tp_status;
    }

    public CentroCusto getCentroCusto() {
        return centroCusto;
    }

    public void setCentroCusto(CentroCusto centroCusto) {
        this.centroCusto = centroCusto;
    }

    public Long getId_centrocusto() {
        return id_centrocusto;
    }

    public void setId_centrocusto(Long id_centrocusto) {
        this.id_centrocusto = id_centrocusto;
    }

    public String getDs_observacao() {
        return ds_observacao;
    }

    public void setDs_observacao(String ds_observacao) {
        this.ds_observacao = ds_observacao;
    }

    

}
