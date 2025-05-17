package com.dev.manto_sagrado.exception;

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

    // Lida com exceção de produto
    @ExceptionHandler(InvalidProductException.class)
    public ResponseEntity<String> handleInvalidProductException(InvalidProductException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    // Lida com produto não encontrado
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // Lida com falha ao usar metodo InputStream da imagem
    @ExceptionHandler(InputStreamException.class)
    public ResponseEntity<String> handleInputStreamException(InputStreamException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }

    // Lida com imagem não encontrada
    @ExceptionHandler(ImagesNotFoundException.class)
    public ResponseEntity<String> handleImageNotFoundException(ImagesNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // Lida com cliente não encontrado
    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<String> handleClientNotFoundException(ClientNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // Lida com endereço não encontrado
    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<String> handleAddressNotFoundException(AddressNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidAddressTypeException.class)
    public ResponseEntity<String> handleInvalidAddressTypeException(InvalidAddressTypeException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidClientNameException.class)
    public ResponseEntity<String> handleInvalidClientNameException(InvalidClientNameException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }
}
