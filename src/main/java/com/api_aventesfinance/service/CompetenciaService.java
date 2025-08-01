package com.api_aventesfinance.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api_aventesfinance.enums.StatusCompetencia;
import com.api_aventesfinance.model.Competencia;
import com.api_aventesfinance.repository.CompetenciaRepository;

@Service
public class CompetenciaService {

    @Autowired
    private CompetenciaRepository repository;

    public Competencia criarCompetencia(LocalDate data, Long id_cliente) {

        YearMonth ym = YearMonth.from(data);

        String codigo = ym.format(DateTimeFormatter.ofPattern("yyyyMM"));

        if (repository.findByCodigo(codigo, id_cliente).isPresent()) {
            throw new RuntimeException("Competência já existe: " + codigo);
        }

        Competencia c = new Competencia();
        c.setCd_competencia(codigo);
        c.setDt_inicio(ym.atDay(1));
        c.setDt_fim(ym.atEndOfMonth());
        c.setTp_status(StatusCompetencia.ABERTO);
        c.setId_cliente(id_cliente);

        return repository.save(c);
    }

    public Competencia buscarAtual(Long id_cliente) {
        String codigoAtual = YearMonth.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        return repository.findByCodigo(codigoAtual, id_cliente)
                .orElseThrow(() -> new RuntimeException("Competência atual não cadastrada"));
    }

    public List<Competencia> buscarTodos(Long id_cliente) {

        List<Competencia> objetos = (List<Competencia>) repository.findByCliente(id_cliente);
        return objetos;
    }
    public  Optional<Competencia> buscarPorId(Long id_cliente, Long id) {

         Optional<Competencia> objetos =  repository.findByClienteById(id_cliente, id);
        return objetos;
    }
    public Competencia buscarPorCompetencia(String competencia, Long id_cliente) {

        Optional<Competencia> objeto = repository.findByCodigo(competencia, id_cliente);
        return objeto.get();
    }

    public void verificarStatusCompetencia(String codigo, Long id_cliente) throws Exception {

        Competencia objeto = repository.verificarStatusCompetencia(codigo, id_cliente);

        if (objeto != null) {
            if (objeto.getTp_status().equals(StatusCompetencia.ABERTO)) {

            }

            if (objeto.getTp_status().equals(StatusCompetencia.FECHADO)) {
                throw new Exception("A Competência selecionada está fechada.");
            }

        }
        else
        {
            throw new Exception("Não existe Competência aberta.");
        }

    }

    public StatusCompetencia alterarStatusCompetencia(Long id) {
        Optional<Competencia> objeto = repository.findById(id);

        StatusCompetencia antigo = objeto.get().getTp_status();
        StatusCompetencia novo = antigo.equals(StatusCompetencia.ABERTO) ? StatusCompetencia.FECHADO
                : StatusCompetencia.ABERTO;
        objeto.get().setTp_status(novo);
        repository.save(objeto.get());

        return novo;
    }
}
