package br.com.cunha.encontreme.infrastructure.config.security;

import br.com.cunha.encontreme.infra.rest.model.ErrorResponse;
import br.com.cunha.encontreme.infrastructure.utils.ErrorResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;

import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApiKeyExceptionEntryPointTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private Clock clock;

    @InjectMocks
    private ApiKeyExceptionEntryPoint entryPoint;

    private MockHttpServletRequest mockRequest;

    private MockHttpServletResponse mockResponse;

    private static final String FIXED_INSTANT = "2026-05-03T12:00:00Z";

    @BeforeEach
    void setUp() {
        mockRequest = new MockHttpServletRequest();
        mockResponse = new MockHttpServletResponse();
        Clock fixedClock = Clock.fixed(Instant.parse(FIXED_INSTANT), ZoneId.of("UTC"));
        when(clock.getZone()).thenReturn(fixedClock.getZone());
        when(clock.instant()).thenReturn(fixedClock.instant());
    }

    @Test
    void shouldReturnUnauthorizedWithTheRightMessage() throws Exception {
        AuthenticationException exception =  new BadCredentialsException("Invalid API key");
        String expectedJson = """ 
                {
                    "message": "Invalid API key"
                    "status": 401,
                    "timestamp": "%s",
                    "path": "/api/logs"
                }
                """.formatted(FIXED_INSTANT);
        ErrorResponse errorResponse = ErrorResponseUtil.buildErrorResponse(
                "Invalid API key",
                OffsetDateTime.now(clock),
                HttpStatus.UNAUTHORIZED,
                mockRequest
        );
        when(objectMapper.writeValueAsString(errorResponse)).thenReturn(expectedJson);
        entryPoint.commence(mockRequest, mockResponse, exception);

        assertEquals(HttpStatus.UNAUTHORIZED.value(), mockResponse.getStatus());
        assertEquals("application/json", mockResponse.getContentType());

        assertEquals(expectedJson.strip(), mockResponse.getContentAsString().strip());
    }
}
