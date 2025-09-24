package com.example.order_service.config;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE_NAME = "order_exchange";
    public static final String ORDER_CREATED_QUEUE = "order_created_queue";
    public static final String ORDER_CREATED_ROUTING_KEY = "order.created";
    public static final String PAYMENT_SUCCESSFUL_QUEUE = "payment_successful_queue";

    @Bean Queue orderCreatedQueue() { return new Queue(ORDER_CREATED_QUEUE, true); }
    @Bean TopicExchange exchange() { return new TopicExchange(EXCHANGE_NAME); }
    @Bean Binding orderCreatedBinding(Queue orderCreatedQueue, TopicExchange exchange) {
        return BindingBuilder.bind(orderCreatedQueue).to(exchange).with(ORDER_CREATED_ROUTING_KEY);
    }
    @Bean Queue paymentSuccessfulQueue() { return new Queue(PAYMENT_SUCCESSFUL_QUEUE, true); }
    @Bean public MessageConverter jsonMessageConverter() { return new Jackson2JsonMessageConverter(); }
}