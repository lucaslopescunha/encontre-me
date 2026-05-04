package br.com.cunha.encontreme.domain.model;

public record Address (
    String cep,
    String tipoCep,
    String subTipoCep,
    String endereco,
    String bairro,
    String cidade,
    String uf,
    String complemento,
    String codigoIbge
) {
}
