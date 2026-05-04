package br.com.cunha.encontreme.infrastructure.config.security;

import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationFilterTest {

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private FilterChain filterChain;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthenticationFilter authenticationFilter;

    private MockHttpServletRequest mockRequest;

    private MockHttpServletResponse mockResponse;

    @BeforeEach
    void setUp() {
        mockRequest = new MockHttpServletRequest();
        mockResponse = new MockHttpServletResponse();
    }

    @ParameterizedTest
    @CsvSource({
            "/api/v1/cep/12345678",
            "/swagger-ui/index.html",
            "/swagger-ui/",
            "/v3/api-docs/"
    })
    void shouldSkipAuthenticationWhenEndpointIsPublic(String uri) throws Exception {
        mockRequest.setRequestURI(uri);

        authenticationFilter.doFilterInternal(mockRequest, mockResponse, filterChain);
        verify(filterChain).doFilter(mockRequest, mockResponse);
        verifyNoInteractions(authenticationService);
    }

    @Test
    void shouldAuthenticateAndContinueWhenEndpointIsNotPublic() throws Exception {
        mockRequest.setRequestURI("/api/v1/logs/2883");
        when(authenticationService.getAuthentication(mockRequest)).thenReturn(authentication);
        authenticationFilter.doFilterInternal(mockRequest, mockResponse, filterChain);
        verify(authenticationService).getAuthentication(mockRequest);
        verify(filterChain).doFilter(mockRequest, mockResponse);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
        verifyNoMoreInteractions(authenticationService, authentication, filterChain);
    }

}
