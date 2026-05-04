package br.com.cunha.encontreme.infrastructure.adapter.in.web.logs.controller;

import br.com.cunha.encontreme.application.port.in.DeleteLogsUseCase;
import br.com.cunha.encontreme.application.port.in.FilterLogsUseCase;
import br.com.cunha.encontreme.infra.rest.api.LogsApi;
import br.com.cunha.encontreme.infra.rest.model.LogSearchPageResponse;
import br.com.cunha.encontreme.infrastructure.adapter.in.web.logs.mapper.LogMapper;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LogsController implements LogsApi {

    private final FilterLogsUseCase filterLogsUseCase;
    private final DeleteLogsUseCase deleteLogsUseCase;
    private final LogMapper mapper;

    @Override
    @RateLimiter(name = "logConsultas")
    public ResponseEntity<Void> deleteAllLogs() {
        this.deleteLogsUseCase.deleteAll();
        return ResponseEntity.noContent().build();
    }

    @Override
    @RateLimiter(name = "logConsultas")
    public ResponseEntity<Void> deleteLogsByCep(String cep) {
        this.deleteLogsUseCase.deleteByCep(cep);
        return ResponseEntity.noContent().build();
    }


    @Override
    @RateLimiter(name = "logConsultas")
    public ResponseEntity<LogSearchPageResponse> searchLogs(Integer page,
                                                            Integer size,
                                                            @Nullable String cep,
                                                            @Nullable OffsetDateTime startDate,
                                                            @Nullable OffsetDateTime endDate) throws Exception {
        Instant startInstant = (startDate != null) ? startDate.toInstant() : null;
        Instant endInstant = (endDate != null) ? endDate.toInstant() : null;

        var logs = filterLogsUseCase.filter(
                page,
                size,
                startInstant,
                endInstant,
                cep);
        var response = mapper.toPageResponse(logs, cep);
        return ResponseEntity.ok(response);
    }


}