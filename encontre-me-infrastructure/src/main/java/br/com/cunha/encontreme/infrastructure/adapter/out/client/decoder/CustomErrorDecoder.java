package br.com.cunha.encontreme.infrastructure.adapter.out.client.decoder;

import br.com.cunha.encontreme.domain.exception.CepNotFoundException;
import br.com.cunha.encontreme.application.exception.CepServiceTimeoutException;
import br.com.cunha.encontreme.application.exception.CepServiceUnavailableException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        var status = response.status();

        if (status == 404) {
            return new CepNotFoundException("CEP não encontrado");
        }

        if (status == 408 || status == 504) {
            return new CepServiceTimeoutException("Timeout no serviço de CEP");
        }

        if (status >= 500) {
            return new CepServiceUnavailableException("Serviço de CEP indisponível");
        }

        return defaultDecoder.decode(methodKey, response);
    }
}