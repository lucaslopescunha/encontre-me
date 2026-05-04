package br.com.cunha.encontreme.application.port.in;

import br.com.cunha.encontreme.domain.model.LogSearch;
import br.com.cunha.encontreme.domain.model.PageResult;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public interface FilterLogsUseCase {
    PageResult<LogSearch> filter(Integer page,
                                 Integer size,
                                 Instant startDate,
                                 Instant endDate,
                                 String cep);

}
