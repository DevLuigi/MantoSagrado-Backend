package com.dev.manto_sagrado.exception;

import com.dev.manto_sagrado.exception.UserDeactivatedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice  // Intercepta exceções em todos os controllers
public class GlobalExceptionHandler {

    // Lida com exceção de usuário desativado
    @ExceptionHandler(UserDeactivatedException.class)
    public ResponseEntity<String> handleUserDeactivatedException(UserDeactivatedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    // Lida com exceção de CPF inválido
    @ExceptionHandler(InvalidCpfException.class)
    public ResponseEntity<String> handleInvalidCpfException(InvalidCpfException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }

    // Lida com exceções inesperadas (genéricas)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Um erro inesperado aconteceu.");
    }
}
