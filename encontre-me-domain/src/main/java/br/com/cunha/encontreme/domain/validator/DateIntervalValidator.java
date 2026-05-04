package br.com.cunha.encontreme.domain.validator;

import br.com.cunha.encontreme.domain.exception.DateIntervalException;

import java.time.Instant;
import java.time.OffsetDateTime;

public final class DateIntervalValidator {

    private DateIntervalValidator() {
    }

    public static void validate(Instant startDate, Instant endDate) {
        if (endDate != null && startDate == null) {
            throw new DateIntervalException("O parâmetro 'startDate' é obrigatório quando 'endDate' for informado.");
        }

        if (startDate != null && endDate != null && endDate.isBefore(startDate)) {
            throw new DateIntervalException("A data inicial (startDate) não pode ser posterior à data final (endDate).");
        }
    }
}
