package com.outercode.order.api.controllers;

import com.outercode.order.api.entities.Order;
import com.outercode.order.api.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Orders", description = "Create an Order ")
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "create an Order", description = "This feature create an Order", responses = {
            @ApiResponse(responseCode = "201", description = "Order created successful",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class))),
    })
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        logger.info("Pedido recebido: {}", order.toString());
        System.out.println(order.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.enfileiarPedido(order));
    }
}
