package com.ecommerce.aspects;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Aspecto AOP para manejo de excepciones.
 * Intercepta excepciones lanzadas en métodos marcados con @Auditable para registrarlas.
 */
@Aspect
@Component
public class ExceptionAspect {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionAspect.class);

    /**
     * Intercepta excepciones lanzadas en métodos marcados con @Auditable.
     */
    @AfterThrowing(
        pointcut = "@annotation(com.ecommerce.annotations.Auditable)",
        throwing = "exception"
    )
    public void handleException(JoinPoint joinPoint, Throwable exception) {
        Object[] args = joinPoint.getArgs();
        String orderInfo = extractOrderInfo(args);
        
        logger.error("[ERROR] {} falló: {} (Error simulado)", orderInfo, exception.getMessage());
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

