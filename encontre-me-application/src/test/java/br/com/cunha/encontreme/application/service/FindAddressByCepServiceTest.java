package br.com.cunha.encontreme.application.service;

import br.com.cunha.encontreme.application.port.out.FindAddressByCepPort;
import br.com.cunha.encontreme.domain.model.Address;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FindAddressByCepServiceTest {


    @Mock
    private FindAddressByCepPort findAddressByCepPort;

    @InjectMocks
    private FindAddressByCepService service;

    @Test
    void shouldFindAddressByCep() {
        var cep = "00000000";
        var tipoCep = "LOG";
        var subTipoCep = "PAD";
        var endereco = "Av Paulista";
        var bairro = "Jardins";
        var cidade = "São Paulo";
        var uf = "SP";
        var complemento = "Até num 100 lado direito";
        var codigoIbge = "2802308";
        var address = new Address(cep, tipoCep, subTipoCep, endereco, bairro, cidade, uf, complemento, codigoIbge);

        when(findAddressByCepPort.findByCep(cep)).thenReturn(address);

        var result = service.findAddressByCep(cep);

        assertEquals(result, address);
        verify(findAddressByCepPort).findByCep(cep);
    }

    @AfterEach
    public void tearDown() {
        verifyNoMoreInteractions(findAddressByCepPort);
    }
}
