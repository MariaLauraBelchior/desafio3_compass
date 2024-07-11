package com.example.desafio3.service.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import lombok.Getter;
import lombok.Setter;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ErrorResponse errorResponse = new ErrorResponse(400, "Bad Request", "Validation error", errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProdutoInvalidoException.class)
    public ResponseEntity<ErrorResponse> handleProdutoInvalidoException(ProdutoInvalidoException ex) {
        ErrorResponse errorResponse = new ErrorResponse(404, "Not Found", ex.getMessage(), null);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(VendaInvalidaException.class)
    public ResponseEntity<ErrorResponse> handleVendaInvalidaException(VendaInvalidaException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(404, "Not Found", ex.getMessage(), null);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @Setter
    @Getter
    public static class ErrorResponse {
        private int code;
        private String status;
        private String message;
        private LocalDateTime timestamp;
        private Map<String, String> errors;

        public ErrorResponse(int code, String status, String message, Map<String, String> errors) {
            this.code = code;
            this.status = status;
            this.message = message;
            this.timestamp = LocalDateTime.now();
            this.errors = errors;
        }

    }
}
