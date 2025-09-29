package com.example.order_service.controller;

import com.example.order_service.model.Order;
import com.example.order_service.model.OrderStatus;
import com.example.order_service.repository.OrderRepository;
import com.example.order_service.service.OrderEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderRepository orderRepository;
    private final OrderEventPublisher orderEventPublisher;
    private final RestTemplate restTemplate;

    private final String USER_SERVICE_URL = "http://host.minikube.internal:5001/api/v1/users/validate";
    private final String RESTAURANT_SERVICE_URL = "http://host.minikube.internal:5002/api/v1/restaurants/check_stock";
    
    public OrderController(OrderRepository orderRepository, OrderEventPublisher orderEventPublisher, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.orderEventPublisher = orderEventPublisher;
        this.restTemplate = restTemplate;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder() {
        System.out.println("ORDER SERVICE: Received new order request.");

        try {
            restTemplate.postForObject(USER_SERVICE_URL, null, Map.class);
            restTemplate.postForObject(RESTAURANT_SERVICE_URL, null, Map.class);
        } catch (Exception e) {
            System.err.println("Pre-check failed: " + e.getMessage());
            // In a real app, you'd return a proper error object
            return ResponseEntity.status(400).body(null);
        }

        Order newOrder = new Order();
        newOrder.setStatus(OrderStatus.PENDING);
        Order savedOrder = orderRepository.save(newOrder);
        System.out.println("Saved new order with ID: " + savedOrder.getId());

        orderEventPublisher.publishOrderCreatedEvent(savedOrder);

        return ResponseEntity.ok(savedOrder);
    }
}