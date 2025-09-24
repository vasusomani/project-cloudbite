package com.example.payment_service_stub.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "order_exchange";
    public static final String PAYMENT_SUCCESSFUL_QUEUE = "payment_successful_queue";
    public static final String PAYMENT_SUCCESSFUL_ROUTING_KEY = "payment.successful";

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    Queue paymentSuccessfulQueue() {
        return new Queue(PAYMENT_SUCCESSFUL_QUEUE, true);
    }

    @Bean
    Binding paymentSuccessfulBinding(Queue paymentSuccessfulQueue, TopicExchange exchange) {
        return BindingBuilder.bind(paymentSuccessfulQueue).to(exchange).with(PAYMENT_SUCCESSFUL_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}