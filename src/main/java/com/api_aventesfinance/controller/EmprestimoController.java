package com.api_aventesfinance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api_aventesfinance.dto.EmprestimoDTO;
import com.api_aventesfinance.dto.LancamentoDTO;
import com.api_aventesfinance.model.Emprestimo;
import com.api_aventesfinance.model.Lancamento;
import com.api_aventesfinance.repository.EmprestimoRepository;
import com.api_aventesfinance.repository.ItemEmprestimoRepository;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/emprestimo", produces = "application/json")
@Tag(name = "Emprestimo")
public class EmprestimoController  extends BaseController<Emprestimo, EmprestimoDTO, Long> 
 {
    public EmprestimoController(CrudRepository<Emprestimo, Long> repository) {
		super(repository);
	}
    
    @Autowired
    private EmprestimoRepository repository;

    @Autowired
    private ItemEmprestimoRepository itemEmprestimoRepository;

}
