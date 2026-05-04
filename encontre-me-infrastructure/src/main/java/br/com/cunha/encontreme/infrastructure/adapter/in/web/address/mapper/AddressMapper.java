package br.com.cunha.encontreme.infrastructure.adapter.in.web.address.mapper;

import br.com.cunha.encontreme.domain.model.Address;
import br.com.cunha.encontreme.infra.rest.model.AddressResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressResponse toResponse(Address address);



}
