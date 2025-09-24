package com.example.payment_service_stub.service;

import com.example.payment_service_stub.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PaymentEventPublisher {
    private final RabbitTemplate rabbitTemplate;
    public PaymentEventPublisher(RabbitTemplate rabbitTemplate) { this.rabbitTemplate = rabbitTemplate; }
    public void publishPaymentSuccessfulEvent(Long orderId) {
        System.out.println("PAYMENT STUB: Publishing PaymentSuccessfulEvent for Order ID: " + orderId);
        Map<String, Object> event = Map.of("orderId", orderId, "status", "PAID");
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.PAYMENT_SUCCESSFUL_ROUTING_KEY, event);
    }
}
