package com.api_aventesfinance.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api_aventesfinance.dto.CategoriaDTO;
import com.api_aventesfinance.model.Categoria;
import com.api_aventesfinance.repository.CategoriaRepository;

import ch.qos.logback.core.model.Model;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;

import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/categoria", produces = "application/json")
@Tag(name = "Categoria")
public class CategoriaController  {
	
	@Autowired
	private CategoriaRepository repository;

	   @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<?>> obterTodos(  @RequestHeader(value = "X-Cliente", required = false) String id_cliente,
            @RequestHeader(value = "X-Competencia", required = false) String competencia ) 
    {
        List<Categoria> entidades = (List<Categoria>) repository.findAll();
 
        return new ResponseEntity<>(entidades, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> obterPorId(@PathVariable Long id) {
        Optional<Categoria> objeto =  repository.findById(id);


        return new ResponseEntity<>( objeto, HttpStatus.OK);
    }

    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<?> cadastrar(@RequestBody Categoria objeto,   @RequestHeader(value = "X-Cliente", required = false) String id_cliente,
            @RequestHeader(value = "X-Competencia", required = false) String competencia) 
    {
        Categoria objetoSalvo = repository.save(objeto);
    
        return new ResponseEntity<>(objetoSalvo, HttpStatus.CREATED);
    }


	 @DeleteMapping(value = "/{id}", produces = "application/text" )
	public ResponseEntity<?> delete (@PathVariable Long id) throws Exception
	{
        repository.deleteById( id);
			
        return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"Registro deletado!\"}");

	}


	@GetMapping(value = "/sequencia", produces = "application/json")
	@Operation(summary = "Gerar sequencia")
	public ResponseEntity<?> obterSequencia(  @RequestHeader(value = "X-Cliente", required = false) String id_cliente,
            @RequestHeader(value = "X-Competencia", required = false) String competencia) {
		Long ultima_sequencia = repository.obterSequencial();

		Long sq_sequencia = ultima_sequencia + 1;

		return new ResponseEntity<>(sq_sequencia, HttpStatus.OK);
	}

}