package br.com.cunha.encontreme.application.service;

import br.com.cunha.encontreme.application.port.in.FilterLogsUseCase;
import br.com.cunha.encontreme.application.port.out.FindLogsPort;
import br.com.cunha.encontreme.domain.model.LogSearch;
import br.com.cunha.encontreme.domain.model.PageResult;
import br.com.cunha.encontreme.domain.validator.DateIntervalValidator;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class FilterLogsService implements FilterLogsUseCase {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;

    private final FindLogsPort findLogsPort;

    public FilterLogsService(FindLogsPort findLogsPort) {
        this.findLogsPort = findLogsPort;
    }

    @Override
    public PageResult<LogSearch> filter(Integer page,
                                        Integer size,
                                        Instant startDate,
                                        Instant endDate,
                                        String cep) {

        DateIntervalValidator.validate(startDate, endDate);

        var safePage = page == null || page < 0 ? DEFAULT_PAGE : page;
        var safeSize = size == null || size <= 0 ? DEFAULT_SIZE : size;

        return findLogsPort.filter(
                safePage,
                safeSize,
                startDate,
                endDate,
                cep);
    }

}