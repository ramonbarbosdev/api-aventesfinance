package com.api_aventesfinance.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api_aventesfinance.enums.StatusCompetencia;
import com.api_aventesfinance.model.Competencia;
import com.api_aventesfinance.service.CompetenciaService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/competencia")
@Tag(name = "Competencia")
public class CompetenciaController {

    @Autowired
    private CompetenciaService service;

    @PostMapping(value = "/criar", produces = "application/json")
    public ResponseEntity<Competencia> criar(@RequestParam("data") String dataBr) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.parse(dataBr, formatter);

        Competencia c = service.criarCompetencia(data);
        return ResponseEntity.ok(c);
    }

    // @GetMapping(value = "/atual", produces = "application/json")
    // public ResponseEntity<Competencia> atual() {
    //     return ResponseEntity.ok(service.buscarAtual());
    // }

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<?>> todos() {
        List<Competencia> objetos = service.buscarTodos();
        return new ResponseEntity<>(objetos, HttpStatus.OK);
    }
    @GetMapping(value = "/atual", produces = "application/json")
    public ResponseEntity<?> competenciaID(@RequestHeader(value = "X-Competencia", required = false) String competencia) {
        Competencia objeto = service.buscarPorCompetencia(competencia);
        return new ResponseEntity<>(objeto, HttpStatus.OK);
    }

    @GetMapping(value = "/alterar-status/{id}", produces = "application/json")
    public ResponseEntity<?> alterarStatus(@PathVariable Long  id) {
     
        StatusCompetencia novo = service.alterarStatusCompetencia(id);
       return new ResponseEntity<>(Map.of("message", "Competência "+novo), HttpStatus.OK);
    }


}
