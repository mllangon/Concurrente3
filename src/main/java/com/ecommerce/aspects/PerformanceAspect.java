package com.ecommerce.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Aspecto AOP para medir el rendimiento de procesos.
 * Intercepta métodos marcados con @TimedProcess para calcular su tiempo de ejecución.
 */
@Aspect
@Component
public class PerformanceAspect {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceAspect.class);

    /**
     * Intercepta métodos marcados con @TimedProcess para medir su tiempo de ejecución.
     */
    @Around("@annotation(com.ecommerce.annotations.TimedProcess)")
    public Object measurePerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        
        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;
            
            Object[] args = joinPoint.getArgs();
            String orderInfo = extractOrderInfo(args);
            
            logger.info("[PERFORMANCE] {} procesado en {} ms", orderInfo, executionTime);
            
            return result;
        } catch (Throwable e) {
            // Si hay error, no registramos el tiempo de rendimiento
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

