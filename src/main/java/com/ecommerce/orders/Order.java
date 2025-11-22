package com.ecommerce.orders;

/**
 * Clase que representa un pedido en el sistema de comercio electrónico
 */
public class Order {
    private Long id;
    private Double total;
    private String customerName;

    public Order(Long id, Double total, String customerName) {
        this.id = id;
        this.total = total;
        this.customerName = customerName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return "Pedido " + id;
    }
}

