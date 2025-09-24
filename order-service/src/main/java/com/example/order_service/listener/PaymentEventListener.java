package com.example.order_service.listener;

import com.example.order_service.model.OrderStatus;
import com.example.order_service.repository.OrderRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class PaymentEventListener {
    private final OrderRepository orderRepository;
    public PaymentEventListener(OrderRepository orderRepository) { this.orderRepository = orderRepository; }

    @RabbitListener(queues = "payment_successful_queue")
    public void handlePaymentSuccessfulEvent(Map<String, Object> event) {
        Long orderId = ((Number) event.get("orderId")).longValue();
        System.out.println("ORDER SERVICE: Received PaymentSuccessfulEvent for Order ID: " + orderId);
        orderRepository.findById(orderId).ifPresent(order -> {
            order.setStatus(OrderStatus.PAID);
            orderRepository.save(order);
            System.out.println("ORDER SERVICE: Updated Order " + orderId + " status to PAID.");
        });
    }
}