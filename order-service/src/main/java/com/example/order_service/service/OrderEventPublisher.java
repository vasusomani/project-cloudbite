package com.example.order_service.service;

import com.example.order_service.config.RabbitMQConfig;
import com.example.order_service.model.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public OrderEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishOrderCreatedEvent(Order order) {
        System.out.println("Publishing Order Created Event for Order ID: " + order.getId());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ORDER_CREATED_ROUTING_KEY, order);
    }
}