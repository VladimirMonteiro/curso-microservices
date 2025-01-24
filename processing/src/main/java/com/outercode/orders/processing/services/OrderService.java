package com.outercode.orders.processing.services;

import com.outercode.orders.processing.entities.Order;
import com.outercode.orders.processing.entities.OrderItem;
import com.outercode.orders.processing.repositories.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository repository;
    private final ProductService productService;
    private final OrderItemService orderItemService;

    public OrderService(OrderRepository repository, ProductService productService, OrderItemService orderItemService) {
        this.repository = repository;
        this.productService = productService;
        this.orderItemService = orderItemService;
    }

    public void save(Order order) {
        productService.save(order.getItems());

        List<OrderItem> orderItems = orderItemService.save(order.getItems());

        repository.save(order);

        orderItemService.updateListOrder(orderItems, order);
        logger.info("PEDIDO SALVO: {}", order.toString());
    }
}
