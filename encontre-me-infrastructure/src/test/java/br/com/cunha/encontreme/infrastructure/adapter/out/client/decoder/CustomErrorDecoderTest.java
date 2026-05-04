package br.com.cunha.encontreme.infrastructure.adapter.out.client.decoder;

import br.com.cunha.encontreme.application.exception.CepServiceInternalServerError;
import br.com.cunha.encontreme.application.exception.CepServiceTimeoutException;
import br.com.cunha.encontreme.application.exception.CepServiceUnavailableException;
import br.com.cunha.encontreme.domain.exception.CepNotFoundException;
import feign.FeignException;
import feign.Request;
import feign.Response;
import feign.RetryableException;
import org.checkerframework.checker.units.qual.N;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.naming.ServiceUnavailableException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class CustomErrorDecoderTest {


    private CustomErrorDecoder customErrorDecoder;
    private final String methodKey = "methodKey";

    @BeforeEach
    void setUp() {
        customErrorDecoder = new CustomErrorDecoder();
    }

    @Test
    void shouldReturnCustomErrorWhenStatusCodeIs404() {
        Response response = createResponse(404);
        Exception exception = customErrorDecoder.decode(methodKey, response);

        assertNotNull(exception);
        assertThat(exception).isInstanceOf(CepNotFoundException.class);
        assertThat(exception.getMessage()).isEqualTo("CEP não encontrado");
    }

    @Test
    void shouldReturnCustomErrorWhenStatusCodeIs408() {
        Response response = createResponse(408);
        Exception exception = customErrorDecoder.decode(methodKey, response);

        assertNotNull(exception);
        assertThat(exception).isInstanceOf(CepServiceTimeoutException.class);
        assertEquals("Timeout no serviço de CEP", exception.getMessage());
        assertThat(exception.getMessage()).isEqualTo("Timeout no serviço de CEP");
    }

    @Test
    void shouldReturnDefaultErrorWhenStatusCode503() {
        Response response = createResponse(503);
        Exception exception = customErrorDecoder.decode(methodKey, response);

        assertNotNull(exception);
        assertThat(exception).isInstanceOf(CepServiceUnavailableException.class);
        assertThat(exception.getMessage()).isEqualTo("Serviço Indisponível");
    }

    @Test
    void shouldReturnDefaultErrorWhenStatusCode500() {
        Response response = createResponse(500);
        Exception exception = customErrorDecoder.decode(methodKey, response);

        assertNotNull(exception);
        assertThat(exception).isInstanceOf(CepServiceInternalServerError.class);
        assertThat(exception.getMessage()).isEqualTo("Erro interno do servidor");
    }


    @Test
    void shouldHandleEmptyResponseBody() {
        // Arrange
        Response response = Response.builder()
                .status(502)
                .reason("Error")
                .request(buildRequest())
                .headers(Collections.emptyMap())
                .build();

        Exception exception = customErrorDecoder.decode("getcep", response);
        assertNotNull(exception);
        assertThat(exception).isInstanceOf(FeignException.class);
    }

    private Response createResponse(int statusCode) {
        return  Response.builder()
                .status(statusCode)
                .reason("Error")
                .request(buildRequest())
                .headers(Collections.emptyMap())
                .body(new byte[0])
                .build();
    }

    private Request buildRequest() {
        return Request.create(
                Request.HttpMethod.GET,
                "/api/v1/cep/01000000",
                Collections.emptyMap(),
                null,
                StandardCharsets.UTF_8,
                null
        );
    }
}
