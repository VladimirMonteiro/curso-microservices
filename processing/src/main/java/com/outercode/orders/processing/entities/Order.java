package com.outercode.orders.processing.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.outercode.orders.processing.entities.enums.Status;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Entity
@Table(name = "orders")
public class Order {

    @Id
    private UUID id = UUID.randomUUID();
    private String client;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> items = new ArrayList<>();

    @Column(name = "total_value")
    private Double totalValue;

    @Column(name = "email_notification")
    private String emailNotification;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PROCESSING;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateHour = LocalDateTime.now();

    public Order() {
    }

    public Order(UUID id, String client, List<OrderItem> items, Double totalValue, String emailNotification, Status status, LocalDateTime dateHour) {
        this.id = id;
        this.client = client;
        this.items = items;
        this.totalValue = totalValue;
        this.emailNotification = emailNotification;
        this.status = status;
        this.dateHour = dateHour;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public String getEmailNotification() {
        return emailNotification;
    }

    public void setEmailNotification(String emailNotification) {
        this.emailNotification = emailNotification;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getDateHour() {
        return dateHour;
    }

    public void setDateHour(LocalDateTime dateHour) {
        this.dateHour = dateHour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", client='" + client + '\'' +
                ", items=" + items +
                ", totalValue=" + totalValue +
                ", emailNotification='" + emailNotification + '\'' +
                ", status=" + status +
                ", dateHour=" + dateHour +
                '}';
    }
}
