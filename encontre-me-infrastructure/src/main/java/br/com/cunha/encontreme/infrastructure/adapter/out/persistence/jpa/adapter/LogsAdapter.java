package br.com.cunha.encontreme.infrastructure.adapter.out.persistence.jpa.adapter;

import br.com.cunha.encontreme.application.port.out.DeleteLogsPort;
import br.com.cunha.encontreme.application.port.out.FindLogsPort;
import br.com.cunha.encontreme.domain.model.LogSearch;
import br.com.cunha.encontreme.domain.model.PageResult;
import br.com.cunha.encontreme.infrastructure.adapter.out.mapper.LogEntityMapper;
import br.com.cunha.encontreme.infrastructure.adapter.out.persistence.jpa.entity.LogJpaEntity;
import br.com.cunha.encontreme.infrastructure.adapter.out.persistence.jpa.repository.LogJpaRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.OffsetDateTime;

@Component
@RequiredArgsConstructor
public class LogsAdapter implements FindLogsPort, DeleteLogsPort {

    private final LogJpaRepository repository;
    private final LogEntityMapper mapper;

    @Override
    public PageResult<LogSearch> filter(Integer page,Integer size,
                                        OffsetDateTime startDate,
                                        OffsetDateTime endDate,  String cep) {
        var pageable = createPageable(page,
                size);
        var logs = findLogs(cep, startDate, endDate, pageable);

        return toPage(logs);
    }

    @Override
    public void deleteByCep(String cep) {
        repository.deleteByCep(cep);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    private Pageable createPageable(Integer page, Integer size) {
        return PageRequest.of(
                page,
                size,
                Sort.by("searchDate").ascending()
        );
    }

    private Page<LogJpaEntity> findLogs(String cep,
                                        OffsetDateTime startDate,
                                        OffsetDateTime endDate,
                                        Pageable pageable) {
        if (StringUtils.isNotBlank(cep)) {
            return repository.findByCepAndSearchDateInterval(
                    cep,
                    startDate,
                    endDate,
                    pageable
            );
        }

        return repository.findAll(pageable);
    }

    private PageResult<LogSearch> toPage(Page<LogJpaEntity> logs) {
        return new PageResult<>(
                logs.getNumber(),
                logs.getSize(),
                logs.getTotalElements(),
                logs.getTotalPages(),
                logs.getContent()
                        .stream()
                        .map(mapper::toDomain)
                        .toList()
        );
    }
}