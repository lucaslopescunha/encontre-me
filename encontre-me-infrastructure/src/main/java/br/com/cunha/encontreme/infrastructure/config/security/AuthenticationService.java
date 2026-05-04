package br.com.cunha.encontreme.infrastructure.config.security;

import br.com.cunha.encontreme.infrastructure.config.properties.AppProperties;
import br.com.cunha.encontreme.infrastructure.config.properties.SecurityProperties;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {


    private final SecurityProperties securityProperties;

    public Authentication getAuthentication(HttpServletRequest request) {
        var apiKey = request.getHeader(securityProperties.apiHeader());
        if(apiKey == null || !apiKey.equals(securityProperties.apiKey())) {
            throw new BadCredentialsException("API key is required");
        }
        return new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);
    }
}
