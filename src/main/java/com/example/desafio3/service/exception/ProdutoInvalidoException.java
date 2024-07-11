package com.example.desafio3.service.exception;

public class ProdutoInvalidoException extends RuntimeException {
    public ProdutoInvalidoException(String message) {
        super(message);
    }
}
