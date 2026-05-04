package br.com.cunha.encontreme.application.exception;

public class CepServiceInternalServerError extends RuntimeException{
    public CepServiceInternalServerError(String message) {
        super(message);
    }
}
