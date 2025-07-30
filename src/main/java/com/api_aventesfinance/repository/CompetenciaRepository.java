package com.api_aventesfinance.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api_aventesfinance.model.Competencia;
import com.api_aventesfinance.model.Role;

@Repository
public interface CompetenciaRepository extends CrudRepository<Competencia, Long> {

   @Query(value = "SELECT * FROM competencia WHERE cd_competencia = ?1 AND id_cliente = ?2", nativeQuery = true)
    Optional<Competencia> findByCodigo(String codigo, Long id_cliente);

   @Query(value = "SELECT * FROM competencia WHERE id_cliente = ?1", nativeQuery = true)
    List<Competencia> findByCliente(Long id_cliente);

    @Query(value = "SELECT * FROM competencia WHERE cd_competencia = ?1", nativeQuery = true)
    Competencia verificarStatusCompetencia(String codigo);

}