package com.example.payment_service_stub.listener;

import com.example.payment_service_stub.dto.OrderDTO;
import com.example.payment_service_stub.service.PaymentEventPublisher;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {
    private final PaymentEventPublisher paymentEventPublisher;
    public OrderEventListener(PaymentEventPublisher paymentEventPublisher) { this.paymentEventPublisher = paymentEventPublisher; }

    @RabbitListener(queues = "order_created_queue")
    public void handleOrderCreatedEvent(OrderDTO order) {
        System.out.println("PAYMENT STUB: Received OrderCreatedEvent for Order ID: " + order.id());
        paymentEventPublisher.publishPaymentSuccessfulEvent(order.id());
    }
}