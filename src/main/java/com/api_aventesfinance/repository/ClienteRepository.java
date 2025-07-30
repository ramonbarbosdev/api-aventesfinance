package com.api_aventesfinance.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api_aventesfinance.model.Categoria;
import com.api_aventesfinance.model.Cliente;
import com.api_aventesfinance.model.Usuario;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface ClienteRepository extends CrudRepository<Cliente, Long> {

   
    @Query(value = "SELECT * FROM cliente WHERE nu_cnpjcpf = ?1", nativeQuery = true)
    Optional<Cliente> findByNucnpjcpf(String nu_cnpjcpf);

}
