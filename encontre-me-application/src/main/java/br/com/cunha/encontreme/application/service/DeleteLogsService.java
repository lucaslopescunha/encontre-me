package br.com.cunha.encontreme.application.service;

import br.com.cunha.encontreme.application.port.in.DeleteLogsUseCase;
import br.com.cunha.encontreme.application.port.out.DeleteLogsPort;

public class DeleteLogsService implements DeleteLogsUseCase {

    private final DeleteLogsPort deleteLogsPort;

    public DeleteLogsService(DeleteLogsPort deleteLogsPort) {
        this.deleteLogsPort = deleteLogsPort;
    }

    @Override
    public void deleteByCep(String cep) {
        this.deleteLogsPort.deleteByCep(cep);
    }

    @Override
    public void deleteAll() {
        this.deleteLogsPort.deleteAll();
    }
}
