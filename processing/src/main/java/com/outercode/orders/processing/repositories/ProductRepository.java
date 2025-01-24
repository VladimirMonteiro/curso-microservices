package com.outercode.orders.processing.repositories;

import com.outercode.orders.processing.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
