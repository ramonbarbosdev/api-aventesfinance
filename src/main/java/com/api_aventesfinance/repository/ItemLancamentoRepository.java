package com.api_aventesfinance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.api_aventesfinance.model.Categoria;
import com.api_aventesfinance.model.CentroCusto;
import com.api_aventesfinance.model.ItemLancamento;
import com.api_aventesfinance.model.Lancamento;
import com.api_aventesfinance.model.Usuario;


@Repository
@Transactional
public interface ItemLancamentoRepository extends CrudRepository<ItemLancamento, Long> {

    @Query(value = "SELECT CASE WHEN MAX(c.cd_itemlancamento) IS NULL THEN '0' ELSE MAX(c.cd_itemlancamento) END FROM item_lancamento  c WHERE c.id_lancamento = ?1", nativeQuery = true)
    Long obterSequencialById(Long id);

    @Query(value = "SELECT CASE WHEN MAX(c.cd_itemlancamento) IS NULL THEN '0' ELSE MAX(c.cd_itemlancamento) END FROM item_lancamento  c ", nativeQuery = true)
    Long obterSequencial();

    @Query(value = "SELECT cast(1 as boolean) as fl_existe FROM ItemLancamento i WHERE i.cd_itemlancamento = ?1 AND i.id_lancamento = ?2 ")
    Boolean obterSequencialExistente(String codigo, Long id);

    @Query(value = "SELECT * FROM item_lancamento l WHERE l.id_lancamento = ?1", nativeQuery = true)
    List<ItemLancamento> findbyIdItemLancamento(Long id);

    @Modifying
    @Query(value = "DELETE FROM item_lancamento  WHERE id_lancamento = ?1 ",  nativeQuery = true)
    void deleteByIdLancamento(Long id, String competencia);
   

}
