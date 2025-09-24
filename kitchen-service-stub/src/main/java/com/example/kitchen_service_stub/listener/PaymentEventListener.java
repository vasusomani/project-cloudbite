package com.example.kitchen_service_stub.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class PaymentEventListener {
    @RabbitListener(queues = "payment_successful_queue")
    public void handlePaymentSuccessfulEvent(Map<String, Object> event) {
        long orderId = ((Number) event.get("orderId")).longValue();
        System.out.println("KITCHEN STUB: Received PaymentSuccessfulEvent for Order ID: " + orderId);
        System.out.println("KITCHEN STUB: Starting to prepare food...");
    }
}
