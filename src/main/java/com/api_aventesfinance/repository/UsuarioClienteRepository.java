package com.api_aventesfinance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api_aventesfinance.model.Categoria;
import com.api_aventesfinance.model.Cliente;
import com.api_aventesfinance.model.Usuario;
import com.api_aventesfinance.model.UsuarioCliente;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface UsuarioClienteRepository extends CrudRepository<UsuarioCliente, Long> {

   
}
