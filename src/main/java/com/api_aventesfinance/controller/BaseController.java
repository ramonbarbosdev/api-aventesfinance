package com.api_aventesfinance.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public abstract  class  BaseController<T,D,ID> {
    
    protected CrudRepository<T, ID> repository;


    public BaseController(CrudRepository<T, ID> repository)
    {
        this.repository = repository; 
    }

     // ✅ Buscar todos os registros
     @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<?>> obterTodos() 
    {
        List<T> entidades = (List<T>) repository.findAll();
 
        return new ResponseEntity<>(entidades, HttpStatus.OK);
    }

     // ✅ Buscar por ID
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> obterPorId(@PathVariable ID id) {
        Optional<T> objeto =  repository.findById(id);

        // if (!objeto.isPresent())
        // {
        //     throw new MensagemException("Registro não encontrado!");
        // }

        return new ResponseEntity<>( objeto, HttpStatus.OK);
    }

     // ✅ Criar novo registro
    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<?> cadastrar(@RequestBody T objeto) 
    {
        T objetoSalvo = repository.save(objeto);
    
        return new ResponseEntity<>(objetoSalvo, HttpStatus.CREATED);
    }

    @PutMapping(value = "/", produces = "application/json")
    public ResponseEntity<?> atualizar(@RequestBody T objeto) throws Exception
    {
        T objetoSalvo = repository.save(objeto);
    
        return new ResponseEntity<>(objetoSalvo, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = "application/text" )
	public ResponseEntity<?> delete (@PathVariable Long id) throws Exception
	{
        repository.deleteById((ID) id);
			
        return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"Registro deletado!\"}");

	}

   

}