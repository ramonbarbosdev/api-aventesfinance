package com.api_aventesfinance.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api_aventesfinance.enums.StatusEmprestimo;
import com.api_aventesfinance.model.Categoria;
import com.api_aventesfinance.model.CentroCusto;
import com.api_aventesfinance.model.Emprestimo;
import com.api_aventesfinance.model.Lancamento;
import com.api_aventesfinance.model.Usuario;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface EmprestimoRepository extends CrudRepository<Emprestimo, Long> {

    @Query(value = "SELECT CASE WHEN MAX(c.cd_emprestimo) IS NULL THEN '0' ELSE MAX(c.cd_emprestimo) END FROM Emprestimo c", nativeQuery = true)
    Long obterSequencial();

    @Query(value = "SELECT cast(1 as boolean) as fl_existe FROM Emprestimo l WHERE l.cd_emprestimo = ?1 ")
    Boolean obterSequencialExistente(String codigo);

    @Query(value ="SELECT * FROM emprestimo WHERE dt_anomes = ?1", nativeQuery = true)
    List<Emprestimo> buscarObjetoCompetancia(String competencia);

    @Query(value ="SELECT * FROM emprestimo WHERE dt_anomes = ?1 AND id_emprestimo = ?2", nativeQuery = true)
    Emprestimo buscarObjetoCompetanciaId(String competencia, Long id);

}
