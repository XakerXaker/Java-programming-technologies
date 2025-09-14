package com.example.pet_microservice.Config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String PET_REQUEST_QUEUE = "pet.request.queue";
    public static final String PET_RESPONSE_QUEUE = "pet.response.queue";
    public static final String PET_EXCHANGE = "pet.exchange";

    @Bean
    public Queue petRequestQueue() {
        return QueueBuilder.durable(PET_REQUEST_QUEUE).build();
    }

    @Bean
    public Queue petResponseQueue() {
        return QueueBuilder.durable(PET_RESPONSE_QUEUE).build();
    }

    @Bean
    public DirectExchange petExchange() {
        return new DirectExchange(PET_EXCHANGE);
    }

    @Bean
    public Binding petRequestBinding() {
        return BindingBuilder
                .bind(petRequestQueue())
                .to(petExchange())
                .with("pet.request");
    }

    @Bean
    public Binding petResponseBinding() {
        return BindingBuilder
                .bind(petResponseQueue())
                .to(petExchange())
                .with("pet.response");
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
