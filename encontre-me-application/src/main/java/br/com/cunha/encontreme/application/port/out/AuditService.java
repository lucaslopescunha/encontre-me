package br.com.cunha.encontreme.application.port.out;

import br.com.cunha.encontreme.domain.model.Address;
import br.com.cunha.encontreme.domain.model.enumeration.Status;

public interface AuditService {
    void saveAuditLogAsync(String cep, Status status, Address address);

}
