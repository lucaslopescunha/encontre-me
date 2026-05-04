package br.com.cunha.encontreme.infrastructure.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record AppProperties(
        ExternalServices externalServices
) {

    public record ExternalServices(
            Cep cep
    ) {
    }

    public record Cep(
            String url,
            String uriSearch
    ) {
    }



}