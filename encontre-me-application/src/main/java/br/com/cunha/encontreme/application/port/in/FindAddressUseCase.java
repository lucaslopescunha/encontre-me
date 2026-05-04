package br.com.cunha.encontreme.application.port.in;

import br.com.cunha.encontreme.domain.model.Address;

public interface FindAddressUseCase {
    Address findAddressByCep(String cep);
}
