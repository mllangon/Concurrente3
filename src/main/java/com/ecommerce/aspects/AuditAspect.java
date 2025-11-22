package com.ecommerce.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Aspecto AOP para auditoría de procesos.
 * Intercepta métodos marcados con @Auditable para registrar inicio y fin de ejecución.
 */
@Aspect
@Component
public class AuditAspect {

    private static final Logger logger = LoggerFactory.getLogger(AuditAspect.class);

    /**
     * Intercepta métodos marcados con @Auditable para registrar inicio y fin.
     */
    @Around("@annotation(com.ecommerce.annotations.Auditable)")
    public Object auditMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String orderInfo = extractOrderInfo(args);
        
        logger.info("--- Auditoría: Inicio de proceso para {} ---", orderInfo);
        
        try {
            Object result = joinPoint.proceed();
            logger.info("--- Auditoría: Fin de proceso para {} ---", orderInfo);
            return result;
        } catch (Throwable e) {
            // El error ya será manejado por ExceptionAspect
            throw e;
        }
    }

    /**
     * Extrae información del pedido de los argumentos del método.
     */
    private String extractOrderInfo(Object[] args) {
        if (args != null && args.length > 0) {
            for (Object arg : args) {
                if (arg instanceof com.ecommerce.orders.Order) {
                    return arg.toString();
                }
            }
        }
        return "Proceso desconocido";
    }
}

