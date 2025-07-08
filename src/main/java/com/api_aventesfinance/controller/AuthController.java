package com.api_aventesfinance.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api_aventesfinance.dto.auth.AuthLoginDTO;
import com.api_aventesfinance.dto.auth.AuthRegisterDTO;
import com.api_aventesfinance.model.Usuario;
import com.api_aventesfinance.repository.UsuarioRepository;
import com.api_aventesfinance.security.JWTTokenAutenticacaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/auth")
@Tag(name = "Authenticação")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JWTTokenAutenticacaoService jwtTokenAutenticacaoService;

    // @Autowired
    // private PresenceService presenceService;

    // @Autowired
    // private SimpMessagingTemplate messagingTemplate;

    @Operation(summary = "Autenticação de usuario", description = "Faz login com login e senha")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticação aceita"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity login(@RequestBody AuthLoginDTO obj, HttpServletResponse response) {
        String login = obj.getLogin();
        String senha = obj.getSenha();

        var usernamePassword = new UsernamePasswordAuthenticationToken(login, senha);
        var auth = this.authenticationManager.authenticate(usernamePassword);
        Usuario objeto = usuarioRepository.findUserByLogin(login);
        try {
            String token = jwtTokenAutenticacaoService.addAuthentication(response, auth.getName());

            // presenceService.userConnected(objeto.getId());
            // messagingTemplate.convertAndSend("/topic/amigos", "update");

            return ResponseEntity
                    .ok()
                    .body(Map.of(
                            "Authorization", token,
                            "id_usuario", objeto.getId(),
                            "login", objeto.getLogin(),
                            "nm_usuario", objeto.getNome() != null ? objeto.getNome() : ""));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Usuário ou senha invalidos"));
        }

    }

    @Operation(summary = "Criaçao de usuario", description = "Faz registro do usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario criado"),
            @ApiResponse(responseCode = "409", description = "Usuario ja existe")
    })
    @PostMapping(value = "/register", produces = "application/json")
    public ResponseEntity register(@RequestBody AuthRegisterDTO obj)
    {
        String login = obj.getLogin();
        String nome = obj.getNome();
        String senha = obj.getSenha();

        if(login.isEmpty() ) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "O Login não pode ser vazio!"));
        if( nome.isEmpty() ) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "O Nome não pode ser vazio!"));
        if( senha.isEmpty()) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "A Senha não pode ser vazio!"));

        if (usuarioRepository.findUserByLogin(login) != null) return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "Usuário já existe!"));

        String senhaCriptografada = new BCryptPasswordEncoder().encode(senha);

        Usuario usuario = new Usuario();
        usuario.setLogin(login);
        usuario.setNome(nome);
        usuario.setSenha(senhaCriptografada);

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.CREATED);
    }

    @PostMapping(value = "/logout", produces = "application/json")
    public ResponseEntity<?> logout(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Usuario usuario = usuarioRepository.findUserByLogin(username);

            // presenceService.userDisconnected(usuario.getId());
            // messagingTemplate.convertAndSend("/topic/amigos", "update");

            return ResponseEntity.ok(Map.of("message", "Logout realizado com sucesso."));
        }
        return ResponseEntity.status(401).body(Map.of("message", "Usuário não autenticado."));
    }

}
