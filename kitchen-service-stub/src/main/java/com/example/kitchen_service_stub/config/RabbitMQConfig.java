package com.example.kitchen_service_stub.config;
// ... imports for Queue, MessageConverter etc.
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean public MessageConverter jsonMessageConverter() { return new Jackson2JsonMessageConverter(); }
}