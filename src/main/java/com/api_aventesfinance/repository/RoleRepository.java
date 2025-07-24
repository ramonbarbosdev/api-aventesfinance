package com.api_aventesfinance.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api_aventesfinance.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    // @Query(value="DELETE FROM  role WHERE id in (SELECT id FROM usuarios_role  WHERE usuario_id = ?1)", nativeQuery = true)
    // void deleteUsuarioRoleByUsuario(Long id);

    Role findByNomeRole(String nomeRole);
}