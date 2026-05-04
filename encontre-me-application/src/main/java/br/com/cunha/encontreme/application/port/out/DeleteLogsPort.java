package br.com.cunha.encontreme.application.port.out;

public interface DeleteLogsPort {
    void deleteByCep(String cep);

    void deleteAll();
}
