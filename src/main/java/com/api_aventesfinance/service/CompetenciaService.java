package com.api_aventesfinance.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api_aventesfinance.enums.StatusCompetencia;
import com.api_aventesfinance.model.Competencia;
import com.api_aventesfinance.repository.CompetenciaRepository;

@Service
public class CompetenciaService {

    @Autowired
    private CompetenciaRepository repository;

    public Competencia criarCompetencia(LocalDate data) {

        YearMonth ym = YearMonth.from(data);

        String codigo = ym.format(DateTimeFormatter.ofPattern("yyyyMM"));

        if (repository.findByCodigo(codigo).isPresent()) {
            throw new RuntimeException("Competência já existe: " + codigo);
        }

        Competencia c = new Competencia();
        c.setCd_competencia(codigo);
        c.setDt_inicio(ym.atDay(1));
        c.setDt_fim(ym.atEndOfMonth());
        c.setTp_status(StatusCompetencia.ABERTO);

        return repository.save(c);
    }

    public Competencia buscarAtual() {
        String codigoAtual = YearMonth.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        return repository.findByCodigo(codigoAtual)
                .orElseThrow(() -> new RuntimeException("Competência atual não cadastrada"));
    }

    public List<Competencia> buscarTodos() {

        List<Competencia> objetos = (List<Competencia>) repository.findAll();
        return objetos;
    }

    public void verificarStatusCompetencia(String codigo) throws Exception {

        Competencia objeto = repository.verificarStatusCompetencia(codigo);

        if (objeto != null) {
            if (objeto.getTp_status().equals(StatusCompetencia.ABERTO)) {
                
            }

            if (objeto.getTp_status().equals(StatusCompetencia.FECHADO)) {
                throw new Exception("A Competência selecionada está fechada.");
            }

        }

    }

}
