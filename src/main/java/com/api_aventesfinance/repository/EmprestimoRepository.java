package com.api_aventesfinance.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

import com.api_aventesfinance.model.Emprestimo;


import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface EmprestimoRepository extends CrudRepository<Emprestimo, Long> {

    @Query(value = "SELECT CASE WHEN MAX(c.cd_emprestimo) IS NULL THEN '0' ELSE MAX(c.cd_emprestimo) END FROM Emprestimo c where c.dt_anomes = ?1 AND id_cliente = ?2", nativeQuery = true)
    Long obterSequencial(String competencia, Long id_cliente);

    @Query(value = "SELECT cast(1 as boolean) as fl_existe FROM Emprestimo l WHERE l.cd_emprestimo = ?1  and  l.dt_anomes = ?2 AND id_cliente = ?3 ")
    Boolean obterSequencialExistente(String codigo, String competencia, Long id_cliente);

    @Query(value ="SELECT * FROM emprestimo WHERE dt_anomes = ?1 AND id_cliente = ?2", nativeQuery = true)
    List<Emprestimo> buscarObjetoCompetancia(String competencia, Long id_cliente);

    @Query(value ="SELECT * FROM emprestimo WHERE dt_anomes = ?1 AND id_emprestimo = ?2 AND id_cliente = ?3", nativeQuery = true)
    Emprestimo buscarObjetoCompetanciaId(String competencia, Long id, Long id_cliente);

}
