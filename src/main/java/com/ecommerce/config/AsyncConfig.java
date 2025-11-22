package com.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Configuración para habilitar el procesamiento asíncrono.
 * Define un pool de hilos para ejecutar tareas concurrentes.
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    /**
     * Configura un Executor para procesar tareas asíncronas.
     * Permite procesar múltiples pedidos simultáneamente.
     */
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("order-processor-");
        executor.initialize();
        return executor;
    }
}

