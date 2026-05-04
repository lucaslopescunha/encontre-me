package br.com.cunha.encontreme.application.exception;

public class CepRateLimitExceededException extends RuntimeException{

    public CepRateLimitExceededException(String message) {
        super(message);
    }
}
