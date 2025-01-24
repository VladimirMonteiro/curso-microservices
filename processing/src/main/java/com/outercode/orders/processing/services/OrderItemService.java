package com.outercode.orders.processing.services;

import com.outercode.orders.processing.entities.Order;
import com.outercode.orders.processing.entities.OrderItem;
import com.outercode.orders.processing.repositories.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    private final OrderItemRepository repository;

    public OrderItemService(OrderItemRepository repository) {
        this.repository = repository;
    }


    public List<OrderItem> save(List<OrderItem> items) {
        return repository.saveAll(items);
    }

    public void save(OrderItem item) {
        repository.save(item);
    }

    public void updateListOrder(List<OrderItem> orderItems, Order order) {

        orderItems.forEach(item -> {
            item.setOrder(order);
            this.save(item);
        });
    }
}
