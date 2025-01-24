package com.outercode.orders.processing.repositories;

import com.outercode.orders.processing.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
