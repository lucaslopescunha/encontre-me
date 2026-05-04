package br.com.cunha.encontreme.application.service;

import br.com.cunha.encontreme.application.port.out.DeleteLogsPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class DeleteLogsServiceTest {

    @InjectMocks
    private DeleteLogsService deleteLogsService;

    @Mock
    private DeleteLogsPort deleteLogsPort;

    @Test
    void shouldDeleteLogsByCepSuccessfully() {
        var cep = "00000000";

        deleteLogsService.deleteByCep(cep);
        verify(deleteLogsPort).deleteByCep(cep);
    }

    @Test
    void shouldDeleteAllLogsSuccessfully() {
        deleteLogsService.deleteAll();

        verify(deleteLogsPort).deleteAll();
    }

    void tearDown() {
        verifyNoMoreInteractions(deleteLogsPort);
    }

}
