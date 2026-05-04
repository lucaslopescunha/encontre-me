package br.com.cunha.encontreme.infrastructure.aspect;

import br.com.cunha.encontreme.application.port.out.AuditService;
import br.com.cunha.encontreme.domain.model.Address;
import br.com.cunha.encontreme.domain.model.enumeration.Status;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuditAspectTest {

    @Mock
    private AuditService auditService;

    @Mock
    private ProceedingJoinPoint jointPoint;

    @InjectMocks
    private AuditAspect auditAspect;

    private static final String CEP = "00000000";


    @Test
    void shouldSaveWithSuccessWhenServiceIsCalled() throws Throwable {
        Address mockAddress = getAddress();

        when(jointPoint.getArgs()).thenReturn(new Object[]{CEP});
        when(jointPoint.proceed()).thenReturn(mockAddress);

        Object result = auditAspect.saveLog(jointPoint);

        assertNotNull(result);
        assertEquals(mockAddress, result);
        verify(auditService).saveAuditLogAsync(
                eq(CEP),
                eq(Status.SUCCESS),
                eq(mockAddress)
        );

    }

    @Test
    void shouldSaveWithErrorWhenInterceptedServiceThrowsException() throws Throwable {
        RuntimeException exception = new RuntimeException("CEP service exception");

        when(jointPoint.getArgs()).thenReturn(new Object[]{CEP});
        when(jointPoint.proceed()).thenThrow(exception);

        RuntimeException thrownException = assertThrows(
                RuntimeException.class,
                () -> auditAspect.saveLog(jointPoint)
        );

        assertEquals("CEP service exception", thrownException.getMessage());

        verify(jointPoint).getArgs();
        verify(jointPoint).proceed();
        verify(auditService).saveAuditLogAsync(
                eq(CEP),
                eq(Status.ERROR),
                isNull()
        );
    }

    @Test
    void shouldProceedWhenCepIsNull() throws Throwable {
        when(jointPoint.getArgs()).thenReturn(new Object[]{null});
        when(jointPoint.proceed()).thenReturn(null);

        Object result = auditAspect.saveLog(jointPoint);

        assertNull(result);
        verify(jointPoint).getArgs();
        verify(jointPoint).proceed();
        verify(auditService).saveAuditLogAsync(
                isNull(),
                eq(Status.SUCCESS),
                isNull()
        );
    }

    @Test
    void shouldProceedWhenProceedIsString() throws Throwable {
        String proceedResult = "Returning a string";
        when(jointPoint.getArgs()).thenReturn(new Object[]{CEP});
        when(jointPoint.proceed()).thenReturn(proceedResult);

        Object result = auditAspect.saveLog(jointPoint);

        assertEquals(proceedResult, result);
        verify(jointPoint).getArgs();
        verify(jointPoint).proceed();
        verify(auditService).saveAuditLogAsync(
                eq(CEP),
                eq(Status.SUCCESS),
                isNull()
        );
    }

    private Address getAddress() {
        return new Address(
                "01000000",
                "TESTE",
                "NORMAL",
                "Rua do Ouro",
                "Serra",
                "BH",
                "MG",
                "Apto 101",
                "30450740"
        );
    }
}
