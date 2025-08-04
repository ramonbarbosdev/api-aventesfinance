package com.api_aventesfinance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api_aventesfinance.model.Categoria;
import com.api_aventesfinance.model.CentroCusto;
import com.api_aventesfinance.model.Usuario;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface CentroCustoRepository extends CrudRepository<CentroCusto, Long> {

    @Query( value = "SELECT * FROM centro_custo WHERE id_cliente = ?1  ORDER BY id_centrocusto", nativeQuery = true )
    List<CentroCusto> findByAllCliente(Long id_cliente);

    @Query(value = "SELECT CASE WHEN MAX(c.cd_centrocusto) IS NULL THEN '0' ELSE MAX(c.cd_centrocusto) END FROM centro_custo c WHERE id_cliente = ?1", nativeQuery = true)
    Long obterSequencial(Long id_cliente);
}
