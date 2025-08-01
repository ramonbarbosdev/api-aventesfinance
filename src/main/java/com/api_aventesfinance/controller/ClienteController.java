package com.api_aventesfinance.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api_aventesfinance.dto.CategoriaDTO;
import com.api_aventesfinance.dto.ClienteDTO;
import com.api_aventesfinance.dto.UsuarioClienteDTO;
import com.api_aventesfinance.dto.UsuarioDTO;
import com.api_aventesfinance.model.Categoria;
import com.api_aventesfinance.model.Cliente;
import com.api_aventesfinance.model.Usuario;
import com.api_aventesfinance.model.UsuarioCliente;
import com.api_aventesfinance.repository.CategoriaRepository;
import com.api_aventesfinance.repository.ClienteRepository;
import com.api_aventesfinance.repository.UsuarioClienteRepository;
import com.api_aventesfinance.repository.UsuarioRepository;

import ch.qos.logback.core.model.Model;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;

import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(value = "/cliente", produces = "application/json")
@Tag(name = "Cliente")
public class ClienteController extends BaseController<Cliente, ClienteDTO, Long> {

	@Autowired
	private ClienteRepository objetoRepository;

	@Autowired
	private UsuarioClienteRepository usuarioClienteRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public ClienteController(CrudRepository<Cliente, Long> repository) {
		super(repository);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/cadastrar", produces = "application/json")
	public ResponseEntity<?> create(@RequestBody Cliente objeto) throws Exception {
		Optional<Cliente> objetoExistente = objetoRepository.findByNucnpjcpf(objeto.getNu_cnpjcpf());

		if (objetoExistente.isPresent()) {
			throw new Exception("Já existe cliente com esse CPF/CNPJ");
		}

		return new ResponseEntity<>(repository.save(objeto), HttpStatus.OK);

	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/cadastrar-usuario-cliente", produces = "application/json")
	public ResponseEntity<?> createUsuarioCliente(@RequestBody UsuarioClienteDTO dto) throws Exception {

		Optional<UsuarioCliente> objetoExistente = usuarioClienteRepository.verificarExistencia(dto.getId_usuario(),
				dto.getId_cliente());

		if (objetoExistente.isPresent()) {
			throw new Exception("O usuario já se encontra cadastrado nesse cliente com esse CPF/CNPJ");
		}

		UsuarioCliente objeto = new UsuarioCliente();
		objeto.setId_cliente(dto.getId_cliente());
		objeto.setId_usuario(dto.getId_usuario());
		objeto.setDt_cadatros(LocalDateTime.now());
		objeto.setTp_status(dto.getTp_status());

		if (dto.getId_usuariocliente() != null) {
			Optional<UsuarioCliente> existente = usuarioClienteRepository.findById(dto.getId_usuariocliente());
			objeto.setId_usuariocliente(existente.get().getId_usuariocliente());
		}

		return new ResponseEntity<>(usuarioClienteRepository.save(objeto), HttpStatus.OK);

	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(value = "/remover-usuario-cliente/{id}", produces = "application/json")
	public ResponseEntity<?> removerUsuarioCliente(@PathVariable Long id) throws Exception {

		usuarioClienteRepository.deleteById(id);

		return new ResponseEntity<>(Map.of("message", "Usuario removido do cliente!"), HttpStatus.OK);

	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = "/obter-usuario-cliente/{id_cliente}", produces = "application/json")
	public ResponseEntity<List<?>> buscarUsuarioPorCliente(@PathVariable Long id_cliente) {

		List<UsuarioCliente> objetos = usuarioClienteRepository.findAllByCliente(id_cliente);

		return new ResponseEntity<>(objetos, HttpStatus.OK);
	}

	// @PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = "/obter-usuario-logado/{id_usuario}/{id_cliente}", produces = "application/json")
	public ResponseEntity<?> buscarUsuarioPorClienteUsuario(@PathVariable Long id_usuario,
			@PathVariable Long id_cliente) {

		UsuarioCliente objeto = usuarioClienteRepository.findByUsuarioByCliente(id_usuario, id_cliente);

		return new ResponseEntity<>(objeto, HttpStatus.OK);
	}

	// @PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = "/obter-cliente-usuario/{login}", produces = "application/json")
	public ResponseEntity<List<?>> buscarClientePorUsuario(@PathVariable String login) throws Exception {

		Usuario usuario = usuarioRepository.findUserByLogin(login);

		if (usuario == null) {
			throw new Exception("O usuario informado não possui acesso no sistema.");
		}

		List<UsuarioCliente> objetos = usuarioClienteRepository.findAllByUsuario(usuario.getId());

		return new ResponseEntity<>(objetos, HttpStatus.OK);
	}

}