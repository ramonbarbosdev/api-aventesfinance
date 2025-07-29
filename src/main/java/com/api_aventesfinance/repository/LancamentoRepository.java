package com.api_aventesfinance.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api_aventesfinance.model.Categoria;
import com.api_aventesfinance.model.CentroCusto;
import com.api_aventesfinance.model.Lancamento;
import com.api_aventesfinance.model.Usuario;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface LancamentoRepository extends CrudRepository<Lancamento, Long> {

    @Query(value = "SELECT CASE WHEN MAX(c.cd_lancamento) IS NULL THEN '0' ELSE MAX(c.cd_lancamento) END FROM Lancamento c where c.dt_anomes = ?1", nativeQuery = true)
    Long obterSequencial(String competencia);

    @Query(value = "SELECT cast(1 as boolean) as fl_existe FROM Lancamento l WHERE l.cd_lancamento = ?1 AND l.dt_anomes = ?2 ")
    Boolean obterSequencialExistente(String codigo, String dt_anomes);

    @Query(value = """
                SELECT *
                FROM lancamento l
                WHERE l.dt_anomes = ?1
                  AND l.id_centrocusto = ?2
                  AND (:idLancamento IS NULL OR l.id_lancamento <> :idLancamento)
            """, nativeQuery = true)
    Optional<Lancamento> existeLancamentoPorCentroCustoMes(String dt_anomes, Long id_centrocusto, Long idLancamento);

    @Query(value = """
                SELECT *
                FROM lancamento l
                WHERE l.dt_anomes = ?1
            """, nativeQuery = true)
    List<Lancamento> buscarObjetoCompetancia(String competencia);

    @Query(value = """
                SELECT *
                FROM lancamento l
                WHERE l.dt_anomes = ?1
                AND l.id_lancamento = ?2
            """, nativeQuery = true)
    Lancamento buscarObjetoCompetanciaId(String competencia, Long id);

}
