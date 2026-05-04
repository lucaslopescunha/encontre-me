package br.com.cunha.encontreme.infrastructure.adapter.out.mapper;

import br.com.cunha.encontreme.domain.model.Address;
import br.com.cunha.encontreme.domain.model.LogSearch;
import br.com.cunha.encontreme.infrastructure.adapter.out.persistence.jpa.entity.LogAddressJpaEntity;
import br.com.cunha.encontreme.infrastructure.adapter.out.persistence.jpa.entity.LogJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LogEntityMapper {

    @Mapping(target = "status", source = "responseStatus")
    LogSearch toDomain(LogJpaEntity logDocument);

    LogAddressJpaEntity toEntity(Address address);
}