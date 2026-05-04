package br.com.cunha.encontreme.infrastructure.adapter.out.client;

import br.com.cunha.encontreme.infrastructure.adapter.out.client.response.AddressClientResponse;
import br.com.cunha.encontreme.infrastructure.adapter.out.mapper.AddressClientMapper;
import br.com.cunha.encontreme.infrastructure.client.feign.CepFeignClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CepClientAdapterTest {

    @Mock
    private CepFeignClient cepFeignClient;

    @Mock
    private AddressClientMapper mapper;

    @InjectMocks
    private CepClientAdapter cepClientAdapter;

    @Test
    public void shouldReturnAddressWhenCepIsValid() {
        String cep = "00000000";
        AddressClientResponse response = new AddressClientResponse(
                "00000000",
                "logradouro",
                "Rua teste",
                "Complemento",
                "Bairro",
                "Cidade",
                "Estado",
                "complemento",
                "2802308"
                );
    }

}
