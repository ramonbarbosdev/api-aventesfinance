package com.api_aventesfinance.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api_aventesfinance.dto.auth.AuthLoginDTO;
import com.api_aventesfinance.dto.auth.AuthRegisterDTO;
import com.api_aventesfinance.model.MovimentacaoLancamento;
import com.api_aventesfinance.model.Usuario;
import com.api_aventesfinance.repository.MovimentacaoLancamentoRepository;
import com.api_aventesfinance.repository.UsuarioRepository;
import com.api_aventesfinance.security.JWTTokenAutenticacaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/movimentacao")
@Tag(name = "Authenticação")
public class MovimentacaoLancamentoController {


    @Autowired
    private MovimentacaoLancamentoRepository repository;

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<?> obterMovimentacaoGeral()
    {

        List<MovimentacaoLancamento> lista = (List<MovimentacaoLancamento>) repository.findAll();

        return new ResponseEntity<>(lista, HttpStatus.CREATED);

    }
}
