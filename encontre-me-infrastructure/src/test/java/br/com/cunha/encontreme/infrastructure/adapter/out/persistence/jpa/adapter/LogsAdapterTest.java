package br.com.cunha.encontreme.infrastructure.adapter.out.persistence.jpa.adapter;

import br.com.cunha.encontreme.domain.model.LogSearch;
import br.com.cunha.encontreme.domain.model.PageResult;
import br.com.cunha.encontreme.domain.model.enumeration.Status;
import br.com.cunha.encontreme.infrastructure.adapter.out.mapper.LogEntityMapper;
import br.com.cunha.encontreme.infrastructure.adapter.out.persistence.jpa.entity.LogJpaEntity;
import br.com.cunha.encontreme.infrastructure.adapter.out.persistence.jpa.repository.LogJpaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LogsAdapterTest {

    @Mock
    private LogJpaRepository logJpaRepository;

    @Mock
    private LogEntityMapper logEntityMapper;

    @InjectMocks
    private LogsAdapter logsAdapter;

    private static final String CEP = "00000000";
    private static final Instant START_DATE = Instant.parse("2025-01-01T00:00:00Z");
    private static final Instant END_DATE = Instant.parse("2035-12-31T23:59:59Z");

    @Test
    void shouldFilterLogsByCepAndInterval() {
        int page = 0;
        int size = 10;
        var pageable = PageRequest.of(page, size, Sort.by("searchDate").ascending());

        LogJpaEntity entity = new LogJpaEntity();
        Page<LogJpaEntity> pageResult = new PageImpl<>(List.of(entity), pageable, 1L);
        LogSearch domainLog = new LogSearch(entity.getId(), entity.getSearchDate(),entity.getCep(),  Status.SUCCESS, null);
        when(logJpaRepository
                .findByCepAndSearchDateInterval(CEP,
                START_DATE,
                END_DATE,
                pageable))
                .thenReturn(pageResult);
        when(logEntityMapper.toDomain(entity))
                .thenReturn(domainLog);
        PageResult<LogSearch> result = logsAdapter.filter(page, size, START_DATE, END_DATE, CEP);
        assertNotNull(result);
        assertEquals(0, result.page());
        assertEquals(10, result.size());
        assertEquals(1, result.totalElements());
        assertEquals(1, result.totalPages());
        assertEquals(1, result.content().size());
        assertEquals(domainLog, result.content().getFirst());

    }

    @Test
    void shouldFindAllLogsWhenCepIsBlank() {
        int page = 0;
        int size = 5;
        var pageable = PageRequest.of(page, size, Sort.by("searchDate").ascending());

        Page<LogJpaEntity> mockPage = new PageImpl<>(List.of(), pageable, 0L);

        when(logJpaRepository.findAll(pageable)).thenReturn(mockPage);

        PageResult<LogSearch> result = logsAdapter.filter(page, size, START_DATE, END_DATE, "   ");

        assertNotNull(result);
        assertTrue(result.content().isEmpty());

        verify(logJpaRepository).findAll(pageable);
        verify(logJpaRepository, never()).findByCepAndSearchDateInterval(anyString(), any(), any(), any());
    }

    @Test
    void shouldDeleteAllLogs() {
        logsAdapter.deleteAll();
        verify(logJpaRepository).deleteAll();
    }

    @Test
    void shouldDeleteByLogs() {
        logsAdapter.deleteByCep(CEP);
        verify(logJpaRepository).deleteByCep(CEP);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(logJpaRepository, logEntityMapper);
    }
}
