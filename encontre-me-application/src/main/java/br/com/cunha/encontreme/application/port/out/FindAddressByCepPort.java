package br.com.cunha.encontreme.application.port.out;

import br.com.cunha.encontreme.domain.model.Address;

public interface FindAddressByCepPort {
    Address findByCep(String cep);
}
