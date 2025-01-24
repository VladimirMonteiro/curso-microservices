package com.outercode.orders.processing.services;

import com.outercode.orders.processing.entities.OrderItem;
import com.outercode.orders.processing.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public void save(List<OrderItem> items) {
        items.forEach(item -> {
            repository.save(item.getProduct());
        });
    }
}
