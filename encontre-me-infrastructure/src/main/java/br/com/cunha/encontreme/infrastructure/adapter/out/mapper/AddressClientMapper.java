package br.com.cunha.encontreme.infrastructure.adapter.out.mapper;

import br.com.cunha.encontreme.domain.model.Address;
import br.com.cunha.encontreme.infrastructure.adapter.out.client.response.AddressClientResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressClientMapper {

    Address toDomain(AddressClientResponse response);
}
