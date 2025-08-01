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
import com.api_aventesfinance.model.UsuarioCliente;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface UsuarioClienteRepository extends CrudRepository<UsuarioCliente, Long> {

    @Query(value = "SELECT * FROM usuario_cliente WHERE id_usuario = ?1 AND id_cliente = ?2", nativeQuery = true)
    Optional<UsuarioCliente> verificarExistencia(Long id_usuario, Long id_cliente);

    @Query(value = "SELECT * FROM usuario_cliente WHERE id_usuario = ?1 AND id_cliente = ?2", nativeQuery = true)
    UsuarioCliente findByUsuarioByCliente(Long id_usuario, Long id_cliente);

    @Query(value = "SELECT * FROM usuario_cliente WHERE  id_cliente = ?1", nativeQuery = true)
    List<UsuarioCliente> findAllByCliente(Long id);

    @Query(value = "SELECT * FROM usuario_cliente WHERE  id_usuario = ?1", nativeQuery = true)
    List<UsuarioCliente> findAllByUsuario(Long id);

    @Modifying
    @Query(value = "DELETE  FROM usuario_cliente WHERE  id_usuario = ?1", nativeQuery = true)
    void deleteByIdUsuario(Long id);
}
