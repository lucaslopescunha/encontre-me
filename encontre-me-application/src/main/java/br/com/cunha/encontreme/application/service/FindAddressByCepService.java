package br.com.cunha.encontreme.application.service;

import br.com.cunha.encontreme.application.port.in.FindAddressUseCase;
import br.com.cunha.encontreme.application.port.out.FindAddressByCepPort;
import br.com.cunha.encontreme.domain.model.Address;

public class FindAddressByCepService implements FindAddressUseCase {
    private final FindAddressByCepPort findAddressByCepPort;

    public FindAddressByCepService(FindAddressByCepPort findAddressByCepPort) {
        this.findAddressByCepPort = findAddressByCepPort;
    }
    @Override
    public Address findAddressByCep(String cep) {
        return this.findAddressByCepPort.findByCep(cep);
    }
}
