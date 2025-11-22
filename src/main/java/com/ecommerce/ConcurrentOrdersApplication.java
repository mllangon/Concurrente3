package com.ecommerce;

import com.ecommerce.orders.Order;
import com.ecommerce.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Aplicación principal Spring Boot que simula el procesamiento concurrente de pedidos.
 */
@SpringBootApplication
public class ConcurrentOrdersApplication {

    private static final Logger logger = LoggerFactory.getLogger(ConcurrentOrdersApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ConcurrentOrdersApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(OrderService orderService) {
        return args -> {
            logger.info("=== INICIO DE SIMULACIÓN DE PEDIDOS ===");
            
            long startTime = System.currentTimeMillis();
            
            // Crear lista de pedidos de ejemplo
            List<Order> orders = createSampleOrders();
            
            // Procesar todos los pedidos de forma concurrente
            List<CompletableFuture<Boolean>> futures = new ArrayList<>();
            
            for (Order order : orders) {
                CompletableFuture<Boolean> future = orderService.processOrder(order);
                futures.add(future);
            }
            
            // Esperar a que todos los pedidos terminen y contar resultados
            int successCount = 0;
            int errorCount = 0;
            
            for (int i = 0; i < futures.size(); i++) {
                try {
                    CompletableFuture<Boolean> future = futures.get(i);
                    Boolean result = future.get(10, TimeUnit.SECONDS);
                    if (result != null && result) {
                        successCount++;
                    }
                } catch (Exception e) {
                    errorCount++;
                }
            }
            
            long totalTime = System.currentTimeMillis() - startTime;
            
            logger.info("");
            logger.info("=== PROCESAMIENTO FINALIZADO ===");
            logger.info("Pedidos completados exitosamente: {}", successCount);
            logger.info("Pedidos con error: {}", errorCount);
            logger.info("Tiempo total de simulación: {} ms aprox.", totalTime);
        };
    }

    /**
     * Crea una lista de pedidos de ejemplo para la simulación.
     */
    private List<Order> createSampleOrders() {
        List<Order> orders = new ArrayList<>();
        
        String[] customers = {
            "Ana López", "Carlos Gómez", "Marta Ruiz", "Diego Torres",
            "Laura Fernández", "Pedro Ramírez", "Sofía Medina", "Juan Pérez",
            "Lucía Vargas", "Jorge Castillo"
        };
        
        for (int i = 0; i < 10; i++) {
            double total = 50.0 + (Math.random() * 500.0);
            Order order = new Order((long) (i + 1), total, customers[i]);
            orders.add(order);
        }
        
        return orders;
    }
}

