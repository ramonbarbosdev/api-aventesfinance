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
import org.springframework.security.access.prepost.PreAuthorize;
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

import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/competencia")
@Tag(name = "Competencia")
public class CompetenciaController {

    @Autowired
    private CompetenciaService service;

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<?> criar(@RequestBody Competencia obj, @RequestHeader(value = "X-Cliente", required = false) String id_cliente) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.parse(obj.getDt_competencia(), formatter);

        Competencia c = service.criarCompetencia(data, Long.valueOf(id_cliente));
        return ResponseEntity.ok(c);
    }


    // @GetMapping(value = "/atual", produces = "application/json")
    // public ResponseEntity<Competencia> atual() {
    // return ResponseEntity.ok(service.buscarAtual());
    // }

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<?>> todos(@RequestHeader(value = "X-Cliente", required = false) String id_cliente) {

        List<Competencia> objetos = service.buscarTodos(Long.valueOf(id_cliente));
        return new ResponseEntity<>(objetos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> obterId(@RequestHeader(value = "X-Cliente", required = false) String id_cliente,
            @PathVariable Long id) {

        Optional<Competencia> objetos = service.buscarPorId(Long.valueOf(id_cliente), id);
        return new ResponseEntity<>(objetos, HttpStatus.OK);
    }

    @GetMapping(value = "/atual", produces = "application/json")
    public ResponseEntity<?> competenciaID(@RequestHeader(value = "X-Competencia", required = false) String competencia,
            @RequestHeader(value = "X-Cliente", required = false) String id_cliente) {
        Competencia objeto = service.buscarPorCompetencia(competencia, Long.valueOf(id_cliente));
        return new ResponseEntity<>(objeto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @GetMapping(value = "/alterar-status/{id}", produces = "application/json")
    public ResponseEntity<?> alterarStatus(@PathVariable Long id,
            @RequestHeader(value = "X-Cliente", required = false) String id_cliente) {

        StatusCompetencia novo = service.alterarStatusCompetencia(id);
        return new ResponseEntity<>(Map.of("message", "Competência " + novo), HttpStatus.OK);
    }

}
