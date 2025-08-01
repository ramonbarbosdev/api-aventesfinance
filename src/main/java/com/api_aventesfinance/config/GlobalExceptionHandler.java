package com.api_aventesfinance.config;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import com.api_aventesfinance.model.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        
        
        String mensagemTratada = traduzMensagemViolacao(ex.getMessage());
        ErrorResponse error = new ErrorResponse(
                mensagemTratada,
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);

    }

     private String traduzMensagemViolacao(String mensagemOriginal) {
        if (mensagemOriginal == null) return "Violação de integridade de dados.";
        Pattern pattern = Pattern.compile("violates foreign key constraint \"(.*?)\".*table \"(.*?)\"");
        Matcher matcher = pattern.matcher(mensagemOriginal);
        if (matcher.find()) {
            String constraint = matcher.group(1);
            String tabela = matcher.group(2);
            return String.format("A exclusão falhou: o registro está sendo utilizado na tabela '%s' (constraint: %s).",
                    tabela, constraint);
        }
        return mensagemOriginal;
        // return "Erro de integridade referencial detectado. Verifique se o item está em uso por outras entidades.";
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<?> handleDataIntegrityViolation(TransactionSystemException ex) {
        Throwable rootCause = ex.getRootCause(); // aqui está o erro de verdade

        String detalhes = rootCause != null ? rootCause.getMessage() : "Erro desconhecido na transação JPA";

        ErrorResponse error = new ErrorResponse(
                detalhes,
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);

    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(EntityNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }



   

    // Handler genérico opcional
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
