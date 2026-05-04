package br.com.cunha.encontreme.infrastructure.client.feign;

import br.com.cunha.encontreme.infrastructure.adapter.out.client.response.AddressClientResponse;
import br.com.cunha.encontreme.infrastructure.config.FeignConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "cep-client",
        configuration = FeignConfig.class
)
public interface CepFeignClient {

    @GetMapping("/api/v1/consulta/cep/{cep}")
    AddressClientResponse findCep(@PathVariable String cep);

}