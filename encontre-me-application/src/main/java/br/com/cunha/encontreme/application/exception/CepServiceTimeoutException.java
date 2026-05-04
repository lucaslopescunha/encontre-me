package br.com.cunha.encontreme.application.exception;

public class CepServiceTimeoutException extends RuntimeException {
    public CepServiceTimeoutException(String message) {
        super(message);
    }

}
