package br.com.cunha.encontreme.infrastructure.adapter.in.web.logs.mapper;

import br.com.cunha.encontreme.domain.model.LogSearch;
import br.com.cunha.encontreme.domain.model.PageResult;
import br.com.cunha.encontreme.infra.rest.model.LogSearchPageResponse;
import br.com.cunha.encontreme.infra.rest.model.LogSearchResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface LogMapper {

    @Mapping(target = "searchDate", source = "searchDate")
    @Mapping(target = "responseBody", source = "responseBody")
    LogSearchResponse toResponse(LogSearch log);

    // --- Conversores de Data ---

    default OffsetDateTime mapInstantToOffset(Instant instant) {
        return instant != null ? instant.atOffset(ZoneOffset.UTC) : null;
    }

    default Instant mapOffsetToInstant(OffsetDateTime offsetDateTime) {
        return offsetDateTime != null ? offsetDateTime.toInstant() : null;
    }

    default LogSearchResponse toResponse(LogSearch log, String cep) {
        if (log == null) return null;

        LogSearchResponse response = toResponse(log);
        if (cep == null || cep.isBlank()) {
            response.setResponseBody(null);
        }
        return response;
    }

    default LogSearchPageResponse toPageResponse(PageResult<LogSearch> page, String cep) {
        if (page == null) return null;

        return LogSearchPageResponse.builder()
                .page(page.page())
                .size(page.size())
                .totalElements(page.totalElements())
                .totalPages(page.totalPages())

                .content(page.content().stream().map(log -> toResponse(log, cep)).toList())
                .build();
    }
}
