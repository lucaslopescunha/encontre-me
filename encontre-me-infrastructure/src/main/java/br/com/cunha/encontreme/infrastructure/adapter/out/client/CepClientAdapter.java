package br.com.cunha.encontreme.infrastructure.adapter.out.client;

import br.com.cunha.encontreme.application.port.out.FindAddressByCepPort;
import br.com.cunha.encontreme.domain.model.Address;
import br.com.cunha.encontreme.infrastructure.adapter.out.mapper.AddressClientMapper;
import br.com.cunha.encontreme.infrastructure.client.feign.CepFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CepClientAdapter implements FindAddressByCepPort {

    private final CepFeignClient cepFeignClient;
    private final AddressClientMapper mapper;

    @Override
    public Address findByCep(String cep) {
        log.debug("[CEP_SEARCH_START] cep={}", cep);
        var response = cepFeignClient.findCep(cep);
        log.debug("[CEP_SEARCH_SUCCESS] cep={}", cep);
        return mapper.toDomain(response);
    }

}