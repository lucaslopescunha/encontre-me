package br.com.cunha.encontreme.application.service;

import br.com.cunha.encontreme.application.port.out.FindLogsPort;
import br.com.cunha.encontreme.domain.exception.DateIntervalException;
import br.com.cunha.encontreme.domain.model.LogSearch;
import br.com.cunha.encontreme.domain.model.PageResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FilterLogsServiceTest {

    @Mock
    private FindLogsPort findLogsPort;

    @InjectMocks
    private FilterLogsService service;

    private Instant startDate;
    private Instant endDate;

    @BeforeEach
    void setUp() {
        startDate = Instant.parse("2026-01-01T00:00:00Z");
        endDate = Instant.parse("2026-01-02T23:59:00Z");
    }

    @Test
    void shouldFilterWhenParametersAreValid() {
        var cep = "00000000";
        var page = 2;
        var size = 15;

        var expectedResult = new PageResult<LogSearch>(
                page,
                size,
                0L,
                0,
                List.of());
        when(findLogsPort
                .filter(page,
                        size,
                        startDate,
                        endDate,
                        cep))
                .thenReturn(expectedResult);

        var result =  service.filter(page, size, startDate, endDate, cep);

        assertNotNull(result);
        assertEquals(expectedResult, result);

        verify(findLogsPort).filter(
                page,
                size,
                startDate,
                endDate,
                cep);
    }

    @Test
    void shouldUseDefaultPageAndSizeWhenInvalid() {
        var cep = "10000000";

        var pageResult = new PageResult<LogSearch>(0,
                10,
                0L,
                0,
                List.of());
        when(findLogsPort
                .filter(0, 10, startDate, endDate, cep))
                .thenReturn(pageResult);

        var result = service.filter(-1, 0, startDate, endDate, cep);

        assertEquals(result, pageResult);

        verify(findLogsPort).filter(0, 10, startDate, endDate, cep);
    }

    @Test
    void shouldUseDefaultPageAndSizeWhenNull() {
        var pageResult = new PageResult<LogSearch>(
                0,
                10,
                0L,
                0,
                List.of()
        );
        when(findLogsPort.filter(0, 10, null, null, null))
                .thenReturn(pageResult);

        var result = service.filter(null, null, null, null, null);

        assertEquals(result, pageResult);
        verify(findLogsPort).filter(0, 10, null, null, null);
    }

    @Test
    void shouldThrowExceptionWhenEndDateIsBeforeStartDate() {
        var invalidEndDate = startDate.minus(1, ChronoUnit.DAYS);

        assertThrows(DateIntervalException.class, () -> {
            service.filter(null, null, startDate, invalidEndDate, null);
        });
        verifyNoInteractions(findLogsPort);
    }

    @Test
    void shouldThrowExceptionWhenEndDateIsProvidedWithoutStartDate() {

        assertThrows(DateIntervalException.class, () -> {
            service.filter(null, null, null, endDate, null);
        });
        verifyNoInteractions(findLogsPort);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(findLogsPort);
    }
}
