package com.example.owner_microservice.Config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {
    public static final String OWNER_REQUEST_QUEUE = "owner.request.queue";
    public static final String OWNER_RESPONSE_QUEUE = "owner.response.queue";
    public static final String OWNER_EXCHANGE = "owner.exchange";

    @Bean
    public Queue ownerRequestQueue() {
        return QueueBuilder.durable(OWNER_REQUEST_QUEUE).build();
    }

    @Bean
    public Queue ownerResponseQueue() {
        return QueueBuilder.durable(OWNER_RESPONSE_QUEUE).build();
    }

    @Bean
    public DirectExchange ownerExchange() {
        return new DirectExchange(OWNER_EXCHANGE);
    }

    @Bean
    public Binding ownerRequestBinding() {
        return BindingBuilder
                .bind(ownerRequestQueue())
                .to(ownerExchange())
                .with("owner.request");
    }

    @Bean
    public Binding ownerResponseBinding() {
        return BindingBuilder
                .bind(ownerResponseQueue())
                .to(ownerExchange())
                .with("owner.response");
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

