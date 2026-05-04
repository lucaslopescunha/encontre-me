package br.com.cunha.encontreme.infrastructure.adapter.out.persistence.jpa.repository;

import br.com.cunha.encontreme.infrastructure.adapter.out.persistence.jpa.entity.LogJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.time.OffsetDateTime;

public interface LogJpaRepository extends JpaRepository<LogJpaEntity, Long> {

    @Query(
            value = """
            SELECT l FROM LogJpaEntity l
            JOIN FETCH l.responseBody a
            WHERE (:cep IS NULL OR a.cep = :cep)
              AND (CAST(:startDate as OffsetDateTime) IS NULL OR l.searchDate >= :startDate)
              AND (CAST(:endDate as OffsetDateTime) IS NULL OR l.searchDate <= :endDate)
            """,
            countQuery = """
            SELECT COUNT(l) FROM LogJpaEntity l
            JOIN l.responseBody a
            WHERE (:cep IS NULL OR a.cep = :cep)
              AND (CAST(:startDate as OffsetDateTime) IS NULL OR l.searchDate >= :startDate)
              AND (CAST(:endDate as OffsetDateTime) IS NULL OR l.searchDate <= :endDate)
            """
    )
    Page<LogJpaEntity> findByCepAndSearchDateInterval(
            @Param("cep") String cep,
            @Param("startDate") OffsetDateTime startDate,
            @Param("endDate") OffsetDateTime endDate,
            Pageable pageable
    );

    void deleteByCep(String cep);
}