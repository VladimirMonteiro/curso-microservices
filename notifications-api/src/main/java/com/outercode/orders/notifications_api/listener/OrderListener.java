package com.outercode.orders.notifications_api.listener;


import com.outercode.orders.notifications_api.entities.Order;
import com.outercode.orders.notifications_api.services.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderListener {

    private final Logger logger = LoggerFactory.getLogger(OrderListener.class);
    private  final EmailService emailService;

    public OrderListener(EmailService emailService) {
        this.emailService = emailService;
    }


    @RabbitListener(queues = "pedidos.v1.pedidos-criado-gerar-notificacao")
    public void sendNotification(Order order) {
        emailService.sendEmail(order);

        logger.info("TENTANDO CONSUMIR A MENSAGEM");
        if (order.getTotalValue() == 0) {
            throw new RuntimeException("ERROR OF EXCEPTION");
        }
        logger.info("Notificação gerada: {}", order.toString());

    }
}
