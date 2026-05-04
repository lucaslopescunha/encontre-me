package br.com.cunha.encontreme.infrastructure.adapter.out.persistence.jpa.repository;

import br.com.cunha.encontreme.infrastructure.adapter.out.persistence.jpa.entity.LogJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;

public interface LogJpaRepository extends JpaRepository<LogJpaEntity, Long> {

    @Query("""
            SELECT l FROM LogJpaEntity l
            JOIN FETCH l.responseBody a
            WHERE (:cep IS NULL OR a.cep = :cep)
            AND (true = :#{#startDate == null} OR l.searchDate >= :startDate)
            AND (true = :#{#endDate == null} OR l.searchDate <= :endDate)
            """)
    Page<LogJpaEntity> findByCepAndSearchDateInterval(
            @Param("cep") String cep,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable
    );

    void deleteByCep(String cep);
}