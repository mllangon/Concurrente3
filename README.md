# Sistema de Procesamiento Concurrente de Pedidos con AOP

## Miembros del Grupo
- [Nombre del estudiante 1]
- [Nombre del estudiante 2]
- [Nombre del estudiante 3] (si aplica)

## Descripción del Proyecto

Este proyecto implementa un sistema de comercio electrónico que procesa múltiples pedidos de forma concurrente utilizando Spring Boot y Programación Orientada a Aspectos (AOP). El sistema simula el procesamiento de pedidos con auditoría, control de rendimiento y manejo de excepciones, todo separado de la lógica de negocio principal mediante aspectos AOP.

## Lógica de la Solución

La solución utiliza:
- **Procesamiento asíncrono** con `@Async` y `ExecutorService` para procesar múltiples pedidos simultáneamente
- **Aspectos AOP** para interceptar métodos y aplicar funcionalidades transversales (auditoría, rendimiento, excepciones)
- **Anotaciones personalizadas** (`@Auditable`, `@TimedProcess`) para marcar métodos que deben ser interceptados
- **Simulación de fallos aleatorios** para demostrar el manejo de excepciones

## Estructura de Archivos

### Archivos de Configuración
- **pom.xml**: Configuración Maven con dependencias de Spring Boot y AOP
- **application.properties**: Configuración de logging para mostrar mensajes en consola

### Paquete `orders`
- **Order.java**: Clase que representa un pedido con id, total y nombre del cliente

### Paquete `annotations`
- **Auditable.java**: Anotación personalizada para marcar métodos que deben ser auditados
- **TimedProcess.java**: Anotación personalizada para marcar métodos cuyo tiempo de ejecución debe medirse

### Paquete `aspects`
- **AuditAspect.java**: Aspecto que intercepta métodos `@Auditable` para registrar inicio y fin de procesos
- **PerformanceAspect.java**: Aspecto que intercepta métodos `@TimedProcess` para medir y registrar tiempos de ejecución
- **ExceptionAspect.java**: Aspecto que captura excepciones en métodos `@Auditable` y las registra como errores

### Paquete `service`
- **OrderService.java**: Servicio que procesa pedidos de forma asíncrona, simulando verificación de stock, pago y envío con pausas aleatorias y fallos simulados

### Paquete `config`
- **AsyncConfig.java**: Configuración para habilitar procesamiento asíncrono con pool de hilos

### Clase Principal
- **ConcurrentOrdersApplication.java**: Aplicación Spring Boot que crea 10 pedidos de ejemplo y los procesa concurrentemente, mostrando estadísticas finales

## Cómo Ejecutar

1. Asegúrate de tener Java 17 y Maven instalados
2. Compila el proyecto: `mvn clean install`
3. Ejecuta la aplicación: `mvn spring-boot:run`

La aplicación procesará 10 pedidos de forma concurrente y mostrará en consola:
- Mensajes de auditoría (inicio y fin de procesos)
- Tiempos de rendimiento de cada pedido
- Errores simulados cuando ocurran
- Estadísticas finales del procesamiento
