package com.api_aventesfinance.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api_aventesfinance.model.Categoria;
import com.api_aventesfinance.model.ItemPluggy;
import com.api_aventesfinance.model.Usuario;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface ItemPluggyRepository extends CrudRepository<ItemPluggy, String> {

    @org.springframework.transaction.annotation.Transactional
    @Modifying
    @Query(nativeQuery = true, value = "update item_pluggy set fl_main = ?1 where id_item = ?2")
    void atualizarMain(Boolean fl_main, String id_item);

    Optional<ItemPluggy> findByFlMainTrue();
}
