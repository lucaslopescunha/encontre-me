package br.com.cunha.encontreme.infrastructure.adapter.in.web.exception;

import br.com.cunha.encontreme.domain.exception.CepNotFoundException;
import br.com.cunha.encontreme.application.exception.CepRateLimitExceededException;
import br.com.cunha.encontreme.application.exception.CepServiceTimeoutException;
import br.com.cunha.encontreme.application.exception.CepServiceUnavailableException;
import br.com.cunha.encontreme.infra.rest.model.ErrorResponse;
import br.com.cunha.encontreme.infrastructure.utils.ErrorResponseUtil;
import feign.FeignException;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Clock;
import java.time.OffsetDateTime;

@RestControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler {

    private final Clock clock;

    @ExceptionHandler(RequestNotPermitted.class)
    public ResponseEntity<ErrorResponse> handleRequestNotPermitted(
            CepRateLimitExceededException exception,
            HttpServletRequest request
    ) {
        var errorResponse = buildErrorResponse(
                "Limite de requisições excedido.",
                HttpStatus.TOO_MANY_REQUESTS,
                request
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(CepServiceUnavailableException.class)
    public ResponseEntity<ErrorResponse> handleException(
            CepServiceUnavailableException exception,
            HttpServletRequest request
    ) {
        var errorResponse = buildErrorResponse(
                exception.getMessage(),
                HttpStatus.SERVICE_UNAVAILABLE,
                request
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(CepServiceTimeoutException.class)
    public ResponseEntity<ErrorResponse> handleException(
            CepServiceTimeoutException exception,
            HttpServletRequest request
    ) {
        var errorResponse = buildErrorResponse(
                exception.getMessage(),
                HttpStatus.GATEWAY_TIMEOUT,
                request
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.GATEWAY_TIMEOUT);
    }

    @ExceptionHandler(CepNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            CepNotFoundException exception,
            HttpServletRequest request
    ) {
        var errorResponse = buildErrorResponse(
                exception.getMessage(),
                HttpStatus.NOT_FOUND,
                request
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponse> handleFeignException(
            FeignException exception,
            HttpServletRequest request
    ) {
        var status = mapFeignStatus(exception.status());
        var errorResponse = buildErrorResponse(
                extractMessageFromFeign(exception, status),
                status,
                request
        );

        return new ResponseEntity<>(errorResponse, status);
    }

    private HttpStatus mapFeignStatus(int statusCode) {
        if (statusCode == 404) {
            return HttpStatus.NOT_FOUND;
        }
        if (statusCode >= 500 || statusCode == -1) {
            return HttpStatus.SERVICE_UNAVAILABLE;
        }
        if (statusCode == 408) {
            return HttpStatus.GATEWAY_TIMEOUT;
        }
        return HttpStatus.BAD_GATEWAY;
    }

    private String extractMessageFromFeign(FeignException exception, HttpStatus status) {
        if (status == HttpStatus.NOT_FOUND) {
            return "Recurso não encontrado";
        }
        if (status == HttpStatus.SERVICE_UNAVAILABLE) {
            return "Servidor instável";
        }
        if (status == HttpStatus.GATEWAY_TIMEOUT) {
            return "Timeout no serviço externo";
        }
        return "Falha na comunicação com serviço externo";
    }

    private ErrorResponse buildErrorResponse(
            String message,
            HttpStatus status,
            HttpServletRequest request
    ) {

        return ErrorResponseUtil.buildErrorResponse(
                message,
                OffsetDateTime.now(clock),
                status,
                request
        );
    }
}