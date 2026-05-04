package br.com.cunha.encontreme.infrastructure.config.security;

import br.com.cunha.encontreme.infrastructure.config.properties.SecurityProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private SecurityProperties securityProperties;

    private MockHttpServletRequest mockRequest;

    @InjectMocks
    private AuthenticationService authenticationService;

    private static final String HEADER_NAME = "X-API-KEY";

    private static final String API_KEY = "123456789";

    @Test
    void shouldReturnAuthenticationWhenApiKeyIsCorrect() {
        mockRequest = new MockHttpServletRequest();
        mockRequest.addHeader(HEADER_NAME, API_KEY);
        when(securityProperties.apiHeader()).thenReturn(HEADER_NAME);
        when(securityProperties.apiKey()).thenReturn(API_KEY);
        var authentication = authenticationService.getAuthentication(mockRequest);
        assertNotNull(authentication);
        assertTrue(authentication.isAuthenticated());
        assertEquals(API_KEY, authentication.getPrincipal());
        assertTrue(authentication.getAuthorities().isEmpty());
    }

    @Test
    void shouldThrowBadCredentialsExceptionWhenApiKeyIsIncorrect() {
        mockRequest = new MockHttpServletRequest();
        mockRequest.addHeader(HEADER_NAME, "invalid-api-key");
        when(securityProperties.apiHeader()).thenReturn(HEADER_NAME);
        when(securityProperties.apiKey()).thenReturn(API_KEY);
        assertThrows(BadCredentialsException.class, () -> authenticationService.getAuthentication(mockRequest));
    }

    @Test
    void shouldThrowBadCredentialsExceptionWhenApiKeyIsNull() {
        mockRequest = new MockHttpServletRequest();
        assertThrows(BadCredentialsException.class, () -> authenticationService.getAuthentication(mockRequest));
    }

}
