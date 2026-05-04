package br.com.cunha.encontreme.infrastructure.adapter.out.persistence.jpa.entity;

import br.com.cunha.encontreme.domain.model.Address;
import br.com.cunha.encontreme.domain.model.enumeration.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Table(name = "TB_LOG")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "search_date", nullable = false)
    private OffsetDateTime searchDate;

    @Column(name = "cep", nullable = false)
    private String cep;

    @Enumerated(EnumType.STRING)
    @Column(name = "response_status", nullable = false)
    private Status responseStatus;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private LogAddressJpaEntity responseBody;
}
