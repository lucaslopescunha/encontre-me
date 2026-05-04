package br.com.cunha.encontreme.application.exception;

public class CepInternalServerError extends RuntimeException{
    public CepInternalServerError(String message) {
        super(message);
    }
}
