package br.com.cunha.encontreme.domain.exception;


public class CepNotFoundException extends RuntimeException {

    public CepNotFoundException(String message) {
        super(message);
    }
}