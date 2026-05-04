package br.com.cunha.encontreme.infrastructure.adapter.out.persistence.jpa.repository;

import br.com.cunha.encontreme.infrastructure.adapter.out.persistence.jpa.entity.LogAddressJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogAddressJpaRepository extends JpaRepository<LogAddressJpaEntity, Long> {
}
