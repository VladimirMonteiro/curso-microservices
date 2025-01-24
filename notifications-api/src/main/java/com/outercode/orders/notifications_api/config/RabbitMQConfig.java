package com.outercode.orders.notifications_api.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.queue.name}")
    private String queueName;

    @Value("${rabbitmq.exchange.dlx.name}")
    private String exchangeDlxName;

    @Value("${rabbitmq.queue.qlq.name}")
    private String queueQlqName;


    @Bean
    public FanoutExchange pedidosExchange() {
        return new FanoutExchange(exchangeName);
    }

    @Bean
    public FanoutExchange pedidosDlxExchange() {
        return new FanoutExchange(exchangeDlxName);
    }

    // Configuração da fila de mensagens principais
    @Bean
    public Queue notificationQueue() {
        Map<String, Object> argumentos = new HashMap<>();
        // Configura a fila principal para usar DLX quando uma mensagem for rejeitada ou expirada
        argumentos.put("x-dead-letter-exchange", exchangeDlxName); // Define o exchange DLX
        // Argumento opcional: Configurar a fila do DLX com uma routing-key (se necessário)
        // argumentos.put("x-dead-letter-routing-key", "myRoutingKey");

        // Criando a fila principal que será usada para enviar mensagens
        return new Queue(queueName, true, false, false, argumentos);
    }

    // Configuração da fila de Dead Letter (DLQ)
    @Bean
    public Queue notificationQlqQueue() {
        return new Queue(queueQlqName, true); // A fila DLQ é persistente
    }

    // Binding entre a fila principal e o Exchange DLX
    @Bean
    public Binding bindingDlq() {
        return BindingBuilder.bind(notificationQlqQueue())
                .to(pedidosDlxExchange());
    }

    // Binding entre a fila principal e o Exchange normal
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(notificationQueue())
                .to(pedidosExchange());
    }

    // RabbitAdmin para inicializar o RabbitMQ
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    // Configuração do conversor de mensagem para JSON
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // RabbitTemplate configurado com o conversor de mensagens
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    // Inicializa o RabbitAdmin quando o contexto do Spring Boot for carregado
    @Bean
    public ApplicationListener<ApplicationReadyEvent> applicationReadyEventApplicationListener(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }
}
