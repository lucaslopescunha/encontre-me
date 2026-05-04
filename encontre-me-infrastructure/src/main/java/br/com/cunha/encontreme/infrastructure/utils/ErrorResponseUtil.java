package br.com.cunha.encontreme.infrastructure.utils;

import br.com.cunha.encontreme.infra.rest.model.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;

public class ErrorResponseUtil {

    public static ErrorResponse buildErrorResponse(
            String message,
            OffsetDateTime offsetDateTime,
            HttpStatus status,
            HttpServletRequest request
    ) {
        var errorResponse = new ErrorResponse();
        errorResponse.setMessage(message);
        errorResponse.setStatus(status.value());
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setTimestamp(offsetDateTime);
        return errorResponse;
    }}
