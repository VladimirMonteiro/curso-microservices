package com.outercode.orders.processing.listener;

import com.outercode.orders.processing.entities.Order;
import com.outercode.orders.processing.entities.enums.Status;
import com.outercode.orders.processing.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderListener {

    private final Logger logger = LoggerFactory.getLogger(OrderListener.class);
    private final OrderService orderService;

    public OrderListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = "pedidos.v1.pedidos-criado-gerar-processamento")
    public void saveOrder(Order order) {

        order.setStatus(Status.PROCESSEDED);
        orderService.save(order);
    }
}
