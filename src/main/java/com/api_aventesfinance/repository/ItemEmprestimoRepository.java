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
import com.api_aventesfinance.model.ItemEmprestimo;
import com.api_aventesfinance.model.ItemLancamento;
import com.api_aventesfinance.model.Lancamento;
import com.api_aventesfinance.model.Usuario;


@Repository
@Transactional
public interface ItemEmprestimoRepository extends CrudRepository<ItemEmprestimo, Long> {

    @Query(value = "SELECT CASE WHEN MAX(c.cd_itememprestimo) IS NULL THEN '0' ELSE MAX(c.cd_itememprestimo) END FROM item_emprestimo  c WHERE c.id_emprestimo = ?1", nativeQuery = true)
    Long obterSequencialById(Long id);

    @Query(value = "SELECT CASE WHEN MAX(c.cd_itememprestimo) IS NULL THEN '0' ELSE MAX(c.cd_itememprestimo) END FROM item_emprestimo  c ", nativeQuery = true)
    Long obterSequencial();

    @Query(value = "SELECT cast(1 as boolean) as fl_existe FROM item_emprestimo i WHERE i.cd_itememprestimo = ?1 AND i.id_emprestimo = ?2 ", nativeQuery = true)
    Boolean obterSequencialExistente(String codigo, Long id);



    @Modifying
    @Query(value = "DELETE FROM item_emprestimo  WHERE id_emprestimo = ?1 ",  nativeQuery = true)
    void deleteByIdLancamento(Long id);
   

}
