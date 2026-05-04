package br.com.cunha.encontreme.infrastructure.adapter.out.client;

import br.com.cunha.encontreme.domain.model.Address;
import br.com.cunha.encontreme.infrastructure.adapter.out.client.response.AddressClientResponse;
import br.com.cunha.encontreme.infrastructure.adapter.out.mapper.AddressClientMapper;
import br.com.cunha.encontreme.infrastructure.client.feign.CepFeignClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CepClientAdapterTest {

    @Mock
    private CepFeignClient cepFeignClient;

    @Mock
    private AddressClientMapper mapper;

    @InjectMocks
    private CepClientAdapter cepClientAdapter;

    @Test
    public void shouldReturnAddressWhenCepExists() {
        String cep = "01000000";
        AddressClientResponse mockResponse = new AddressClientResponse(
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
        Address expected = new Address(
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

        when(cepFeignClient.findCep(cep)).thenReturn(mockResponse);
        when(mapper.toDomain(mockResponse)).thenReturn(expected);

        Address result = cepClientAdapter.findByCep(cep);

        assertNotNull(result);
        assertEquals(expected, result);

        verify(cepFeignClient).findCep(cep);
        verify(mapper).toDomain(mockResponse);
    }

    @Test
    void shouldThrowExceptionWhenServiceFails() {
        String cep = "01000000";
        when(cepFeignClient.findCep(cep)).thenThrow(new RuntimeException("Service unavailable"));

        assertThrows(RuntimeException.class, () -> cepClientAdapter.findByCep(cep));
        verify(cepFeignClient).findCep(cep);
        verifyNoInteractions(mapper);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(cepFeignClient, mapper);
    }
}
