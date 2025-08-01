package com.api_aventesfinance.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api_aventesfinance.enums.StatusCompetencia;
import com.api_aventesfinance.model.Competencia;
import com.api_aventesfinance.model.Role;
import com.api_aventesfinance.model.Usuario;
import com.api_aventesfinance.repository.RoleRepository;
import com.api_aventesfinance.repository.UsuarioRepository;
import com.api_aventesfinance.service.CompetenciaService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityManager;

@RestController
@RequestMapping("/role")
@Tag(name = "Role")
public class RoleController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping(value = "/criar-role", produces = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> criarNovaRole(@RequestBody Role objeto) throws Exception {

        Optional<Role> roleExistente = Optional.empty();

        if (objeto.getId() != null) {
            roleExistente = roleRepository.findById(objeto.getId());
        }
        Role roleUser = new Role();
        roleUser.setNomeRole(objeto.getNomeRole());

         roleExistente.ifPresent(existing -> roleUser.setId(existing.getId()));

        roleRepository.save(roleUser);

        return new ResponseEntity<>(roleUser, HttpStatus.OK);
    }

    @PutMapping(value = "/atualizar-role/{id_usuario}/{id_role}", produces = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> atualizarRole(@PathVariable Long id_usuario, @PathVariable Long id_role) throws Exception {

        Optional<Role> roleUser = roleRepository.findById(id_role);
        Optional<Usuario> usuario = usuarioRepository.findById(id_usuario);

        if (usuario.isPresent()) {
            for (Role role : usuario.get().getRoles()) {

                if (!role.getId().equals(id_role)) {
                    throw new Exception(
                            "O usuario " + usuario.get().getNome() + " já possui um papel de " + role.getNomeRole());
                }

                if (role.getId().equals(id_role)) {
                    throw new Exception("Esse papel já está incluso no usuario " + usuario.get().getNome());
                }

            }
        }

        usuario.get().getRoles().add(roleUser.get());

        usuarioRepository.save(usuario.get());

        return new ResponseEntity<>(usuario.get().getRoles(), HttpStatus.OK);
    }

    @GetMapping(value = "/obter-por-usuario/{id_usuario}", produces = "application/json")
    // @PreAuthorize("hasAnyRole('ADMIN')")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> obterRolesUsuario(@PathVariable Long id_usuario) {

        Optional<Usuario> usuario = usuarioRepository.findById(id_usuario);

        return new ResponseEntity<>(usuario.get().getRoles(), HttpStatus.OK);

    }

    @GetMapping(value = "/", produces = "application/json")
    // @PreAuthorize("hasAnyRole('ADMIN')")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> obterTotasRoles() {

        List<Role> usuario = (List<Role>) roleRepository.findAll();

        return new ResponseEntity<>(usuario, HttpStatus.OK);

    }

    @GetMapping(value = "/{id}", produces = "application/json")
    // @PreAuthorize("hasAnyRole('ADMIN')")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> obterRoleId(@PathVariable Long id) {

        Optional<Role> objeto = roleRepository.findById(id);

        return new ResponseEntity<>(objeto, HttpStatus.OK);

    }

    @DeleteMapping(value = "/remover-por-usuario/{id_usuario}", produces = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> removerRolesUsuario(@PathVariable Long id_usuario) {

        roleRepository.deleteByUsuarioId(id_usuario);

        return new ResponseEntity<>(Map.of("message", "Acesso removido do usuario!"), HttpStatus.OK);

    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletar(@PathVariable Long id) {

        roleRepository.deleteById(id);

        return new ResponseEntity<>(Map.of("message", "Acesso excluido!"), HttpStatus.OK);

    }

}
