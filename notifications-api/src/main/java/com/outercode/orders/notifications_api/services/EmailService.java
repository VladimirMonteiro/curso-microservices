package com.outercode.orders.notifications_api.services;

import com.outercode.orders.notifications_api.entities.Order;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(Order order) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom("pedidos-api@company.com");
        simpleMailMessage.setTo(order.getEmailNotification());
        simpleMailMessage.setSubject("Pedido de compra");
        simpleMailMessage.setText(this.genarateMessage(order));
        mailSender.send(simpleMailMessage);
    }

    private String genarateMessage(Order order) {

        String orderId = order.getId().toString();
        String client = order.getClient();
        String value = String.valueOf(order.getTotalValue());
        String status = order.getStatus().name();

        return String.format("Olá %s seu pedido de número %s no valor %s, foi realizado com sucesso." +
                " \nStatus: %s.", client, orderId, value, status);
    }
}
