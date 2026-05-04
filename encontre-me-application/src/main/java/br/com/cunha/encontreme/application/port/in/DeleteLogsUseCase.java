package br.com.cunha.encontreme.application.port.in;

public interface DeleteLogsUseCase {

    void deleteByCep(String cep);

    void deleteAll();
}
