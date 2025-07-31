package com.api_aventesfinance.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api_aventesfinance.dto.UsuarioDTO;
import com.api_aventesfinance.model.Role;
import com.api_aventesfinance.model.Usuario;
import com.api_aventesfinance.model.UsuarioCliente;
import com.api_aventesfinance.repository.RoleRepository;
import com.api_aventesfinance.repository.UsuarioClienteRepository;
import com.api_aventesfinance.repository.UsuarioRepository;

import io.swagger.v3.oas.annotations.tags.Tag;

//@CrossOrigin(origins = "*")
@RestController /* ARQUITETURA REST */
@RequestMapping(value = "/usuario")
@Tag(name = "Usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private RoleRepository roleRepository;

		@Autowired
	private UsuarioClienteRepository usuarioClienteRepository;


	@GetMapping(value = "/obter-todos-usuarios", produces = "application/json")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<?>> buscarTodosUsuarios() {

		List<UsuarioCliente> objetos = (List<UsuarioCliente>) usuarioClienteRepository.findAll();

		return new ResponseEntity<>(objetos, HttpStatus.OK);
	}


	@GetMapping(value = "/{id}", produces = "application/json")
	@CacheEvict(value = "cacheuser", allEntries = true)
	@CachePut("cacheuser")
	public ResponseEntity<UsuarioDTO> init(@PathVariable Long id) {

		Optional<Usuario> usuario = usuarioRepository.findById(id);

		return new ResponseEntity<UsuarioDTO>(new UsuarioDTO(usuario.get()), HttpStatus.OK);
	}

	@GetMapping(value = "/", produces = "application/json")
	@CacheEvict(value = "cacheusuario", allEntries = true) // remover cache nao utilizado
	@CachePut("cacheusuario") // atualizar cache
	public ResponseEntity<List<?>> usuario() throws InterruptedException {
		List<Usuario> usuarios = (List<Usuario>) usuarioRepository.findAll(); // Consulta todos os usuários

		// Mapeia cada objeto Usuario para UsuarioDTO
		List<UsuarioDTO> usuariosDTO = usuarios.stream()
				.map(usuario -> new UsuarioDTO(usuario)) // Usando o construtor para mapear
				.collect(Collectors.toList()); // Coleta todos os DTOs em uma lista

		return new ResponseEntity<>(usuariosDTO, HttpStatus.OK);
	}

	@PostMapping("/{id}/upload-foto")
	public ResponseEntity<?> uploadFotoPerfil(@PathVariable Long id, @RequestParam MultipartFile file) {
		try {
			// Obtém o ano atual
			String anoAtual = String.valueOf(java.time.Year.now().getValue());

			// Caminho da pasta ano dentro da pasta uploads
			Path pastaAno = Path.of("uploads", anoAtual);

			// Verifica se a pasta existe, se não existir, cria
			if (!Files.exists(pastaAno)) {
				Files.createDirectories(pastaAno);
			}

			// Gera nome do arquivo
			String nomeArquivo = UUID.randomUUID() + "_" + file.getOriginalFilename();

			// Caminho completo do arquivo dentro da pasta do ano
			Path caminhoArquivo = pastaAno.resolve(nomeArquivo);

			// Salva o arquivo no caminho definido
			Files.copy(file.getInputStream(), caminhoArquivo);

			// Atualiza usuário com o caminho relativo da imagem
			Usuario usuario = usuarioRepository.findById(id)
					.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
			usuario.setImg("/uploads/" + anoAtual + "/" + nomeArquivo);
			usuarioRepository.save(usuario);

			return new ResponseEntity<>(Map.of("message", "Imagem atualizada com sucesso!"), HttpStatus.OK);

		} catch (IOException e) {
			return new ResponseEntity<>(Map.of("message", "Erro ao salvar imagem"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/perfil/", produces = "application/json")
	public ResponseEntity<?> atualizarPerfil(@RequestBody Usuario usuario) {

		Usuario userTemporario = usuarioRepository.findUserByLogin(usuario.getLogin());

		if (userTemporario == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"Usuário não encontrado.\"}");
		}

		userTemporario.setLogin(usuario.getLogin());
		userTemporario.setNome(usuario.getNome());

		Usuario usuarioSalvo = usuarioRepository.save(userTemporario);

		return new ResponseEntity<>(Map.of("message", "Atualizado com sucesso!"), HttpStatus.OK);
	}

	@PostMapping(value = "/", produces = "application/json")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> cadastrar(@RequestBody Usuario usuario) {

		String senhacriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(senhacriptografada);

		String nomeRole  = usuario.getRoles().iterator().next().getNomeRole();
		Role roleUser = roleRepository.findByNomeRole(nomeRole);
		if (roleUser == null) {
			roleUser = new Role();
			roleUser.setNomeRole(nomeRole);
			roleRepository.save(roleUser);
		}

		usuario.getRoles().clear();
		usuario.getRoles().add(roleUser);

		Usuario userTemporario = usuarioRepository.findUserByLogin(usuario.getLogin());

		if (userTemporario != null) {
			return new ResponseEntity<>(Map.of("message", "O usuario informado já existe!"), HttpStatus.NOT_FOUND);
		}

		Usuario usuarioSalvo = usuarioRepository.save(usuario);

		return new ResponseEntity<>(usuarioSalvo, HttpStatus.OK);
	}

	@PutMapping(value = "/", produces = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> atualizar(@RequestBody Usuario usuario) {

		Usuario userTemporario = usuarioRepository.findUserByLogin(usuario.getLogin());

		if (userTemporario == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"Usuário não encontrado.\"}");
		}

		if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("{\"error\": \"Senha inválida. Não pode ser vazia.\"}");
		}

		if (!userTemporario.getSenha().equals(usuario.getSenha())) {
			String senhacriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
			usuario.setSenha(senhacriptografada);
		}

		Usuario usuarioSalvo = usuarioRepository.save(usuario);

		return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}", produces = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		usuarioRepository.deleteById(id);

		return ResponseEntity.ok(Map.of("message", "Removido com sucesso!"));
	}

}