package br.com.cunha.encontreme.infrastructure.aspect.service;

import br.com.cunha.encontreme.application.port.out.AuditService;
import br.com.cunha.encontreme.domain.model.Address;
import br.com.cunha.encontreme.domain.model.enumeration.Status;
import br.com.cunha.encontreme.infrastructure.adapter.out.mapper.LogEntityMapper;
import br.com.cunha.encontreme.infrastructure.adapter.out.persistence.jpa.entity.LogJpaEntity;
import br.com.cunha.encontreme.infrastructure.adapter.out.persistence.jpa.repository.LogJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final LogJpaRepository repository;

    private final Clock clock;

    private final LogEntityMapper mapper;

    @Async
    public void saveAuditLogAsync(String cep,
                                  Status status,
                                  Address result) {
        var entity = LogJpaEntity.builder()
                .searchDate(Instant.now(clock))
                .cep(cep)
                .responseStatus(status)
                .responseBody(mapper.toEntity(result))
                .build();
        repository.save(entity);
    }
}
