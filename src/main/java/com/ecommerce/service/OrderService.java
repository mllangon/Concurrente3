package com.ecommerce.service;

import com.ecommerce.annotations.Auditable;
import com.ecommerce.annotations.TimedProcess;
import com.ecommerce.orders.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

/**
 * Servicio que gestiona el procesamiento de pedidos de forma concurrente.
 * Utiliza @Async para procesar cada pedido en un hilo independiente.
 */
@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final Random random = new Random();

    /**
     * Procesa un pedido de forma asíncrona.
     * Este método está marcado con @Auditable y @TimedProcess para ser interceptado por los aspectos AOP.
     */
    @Async
    @Auditable
    @TimedProcess
    public CompletableFuture<Boolean> processOrder(Order order) throws InterruptedException {
        logger.info("Pedido {} recibido para el cliente: {}", order.getId(), order.getCustomerName());

        // Simulación de verificación de stock
        simulateOperation("verificación de stock", 500, 1000);
        
        // Simulación aleatoria de fallos (20% de probabilidad)
        if (random.nextDouble() < 0.2) {
            simulateRandomFailure(order);
        }

        // Simulación de procesamiento de pago
        simulateOperation("procesamiento de pago", 300, 800);
        
        // Simulación aleatoria de fallos en el pago (15% de probabilidad)
        if (random.nextDouble() < 0.15) {
            throw new RuntimeException("Pago rechazado");
        }

        // Simulación de preparación de envío
        simulateOperation("preparación de envío", 400, 1200);

        logger.info("Pedido {} completado exitosamente", order.getId());
        return CompletableFuture.completedFuture(true);
    }

    /**
     * Simula una operación con un tiempo aleatorio de espera.
     */
    private void simulateOperation(String operation, int minMs, int maxMs) throws InterruptedException {
        int delay = minMs + random.nextInt(maxMs - minMs);
        Thread.sleep(delay);
    }

    /**
     * Simula un fallo aleatorio durante el procesamiento.
     */
    private void simulateRandomFailure(Order order) {
        String[] errorMessages = {
            "Error al verificar stock",
            "Producto no disponible",
            "Error de conexión con el sistema de inventario"
        };
        String errorMessage = errorMessages[random.nextInt(errorMessages.length)];
        throw new RuntimeException(errorMessage);
    }
}

