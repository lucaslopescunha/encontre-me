package br.com.cunha.encontreme.infrastructure.config.security;

import br.com.cunha.encontreme.infra.rest.model.ErrorResponse;
import br.com.cunha.encontreme.infrastructure.utils.ErrorResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Clock;
import java.time.OffsetDateTime;

@Component
@RequiredArgsConstructor
public class ApiKeyExceptionEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper mapper;

    private final Clock clock;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorResponse errorResponse = ErrorResponseUtil.buildErrorResponse(
                authException.getMessage(),
                OffsetDateTime.now(clock),
                HttpStatus.UNAUTHORIZED,
                request
                );

        response.getWriter().write(mapper.writeValueAsString(errorResponse));

    }
}
