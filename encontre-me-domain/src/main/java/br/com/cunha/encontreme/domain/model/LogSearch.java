package br.com.cunha.encontreme.domain.model;

import br.com.cunha.encontreme.domain.model.enumeration.Status;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public record LogSearch(Long id,
                        OffsetDateTime searchDate,
                        String cep,
                        Status status,
                        Address responseBody) {
}
