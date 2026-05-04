package br.com.cunha.encontreme.infrastructure.config;

import br.com.cunha.encontreme.application.port.in.DeleteLogsUseCase;
import br.com.cunha.encontreme.application.port.in.FindAddressUseCase;
import br.com.cunha.encontreme.application.port.in.FilterLogsUseCase;
import br.com.cunha.encontreme.application.port.out.DeleteLogsPort;
import br.com.cunha.encontreme.application.port.out.FindAddressByCepPort;
import br.com.cunha.encontreme.application.port.out.FindLogsPort;
import br.com.cunha.encontreme.application.service.DeleteLogsService;
import br.com.cunha.encontreme.application.service.FindAddressByCepService;
import br.com.cunha.encontreme.application.service.FilterLogsService;
import br.com.cunha.encontreme.infrastructure.config.properties.AppProperties;
import br.com.cunha.encontreme.infrastructure.config.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

import java.time.Clock;
import java.time.ZoneId;

@Configuration
@EnableConfigurationProperties({AppProperties.class, SecurityProperties.class})
public class ApplicationConfig {

    @Bean
    public FindAddressUseCase findAddressUseCase(FindAddressByCepPort findAddressByCepPort) {
        return new FindAddressByCepService(findAddressByCepPort);
    }

    @Bean
    public FilterLogsUseCase findCepSearchLogsUseCase(FindLogsPort findLogsPort) {
        return new FilterLogsService(findLogsPort);
    }

    @Bean
    public DeleteLogsUseCase deleteLogsUseCase(DeleteLogsPort deleteLogsPort) {
        return new DeleteLogsService(deleteLogsPort);
    }

    @Bean
    public Clock clock() {
        return Clock.system(ZoneId.of("America/Sao_Paulo"));
    }

}
