package br.com.cunha.encontreme.infrastructure.adapter.out.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_LOG_ADDRESS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogAddressJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 8, nullable = false)
    private String cep;

    @Column(name = "tipo_cep")
    private String tipoCep;

    @Column(name = "sub_tipo_cep")
    private String subTipoCep;

    @Column(nullable = false)
    private String endereco;

    private String bairro;
    private String cidade;

    @Column(length = 2)
    private String uf;

    private String complemento;

    @Column(name = "codigo_ibge")
    private String codigoIbge;

}
