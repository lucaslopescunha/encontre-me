package br.com.cunha.encontreme.infrastructure.adapter.in.web.address.controller;

import br.com.cunha.encontreme.application.port.in.FindAddressUseCase;
import br.com.cunha.encontreme.domain.model.Address;
import br.com.cunha.encontreme.infra.rest.api.AddressApi;
import br.com.cunha.encontreme.infra.rest.model.AddressResponse;
import br.com.cunha.encontreme.infrastructure.adapter.in.web.address.mapper.AddressMapper;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
public class AddressController implements AddressApi {

    private final FindAddressUseCase findAddressUseCase;
    private final AddressMapper mapper;

    @Override
    @RateLimiter(name = "cepConsultas")
    public ResponseEntity<AddressResponse> findAddressByCep(String cep) {
        Address address = this.findAddressUseCase.findAddressByCep(cep);
        return ResponseEntity.ok(mapper.toResponse(address));
    }

}