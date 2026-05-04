package br.com.cunha.encontreme.infrastructure.aspect;

import br.com.cunha.encontreme.application.port.out.AuditService;
import br.com.cunha.encontreme.domain.model.Address;
import br.com.cunha.encontreme.domain.model.enumeration.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuditAspect {

    private final AuditService auditService;

    @Around("@annotation(br.com.cunha.encontreme.domain.annotation.Auditable)")
    public Object saveLog(ProceedingJoinPoint joinPoint) throws Throwable {
        var cep = extractCep(joinPoint);

        try {
            var result = joinPoint.proceed();
            processSuccessAudit(cep, result);
            return result;
        } catch (Throwable throwable) {
            processErrorAudit(cep, throwable);
            throw throwable;
        }
    }

    private String extractCep(ProceedingJoinPoint joinPoint) {
        var args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof String cep) {
            return cep;
        }
        return null;
    }

    private void processSuccessAudit(String cep, Object result) {
        var address = extractAddress(result);
        saveAuditLog(cep, Status.SUCCESS, address);
    }

    private void processErrorAudit(String cep, Throwable throwable) {
        log.error("Audit error for CEP: {}", cep, throwable);
        saveAuditLog(cep, Status.ERROR, null);
    }

    private Address extractAddress(Object result) {
        return result instanceof Address address ? address : null;
    }

    private void saveAuditLog(String cep, Status status, Address address) {
        auditService.saveAuditLogAsync(cep, status, address);
    }
}
