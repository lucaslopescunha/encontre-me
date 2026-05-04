package br.com.cunha.encontreme.infrastructure.adapter.out.client.response;

public record AddressClientResponse(
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
