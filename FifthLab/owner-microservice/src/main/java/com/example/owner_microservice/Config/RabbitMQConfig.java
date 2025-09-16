package com.example.owner_microservice.Config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.owner_microservice.DTO.OwnerDTO;
import com.example.owner_microservice.DTO.OwnerRequest;


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
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setClassMapper(classMapper());
        return converter;
    }

    @Bean
    public DefaultClassMapper classMapper() {
        DefaultClassMapper classMapper = new DefaultClassMapper();
        
        // Настраиваем mapping типов
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("ownerDTO", OwnerDTO.class);
        idClassMapping.put("ownerRequest", OwnerRequest.class);
        
        classMapper.setIdClassMapping(idClassMapping);
        classMapper.setTrustedPackages("*"); // Разрешаем все пакеты
        
        return classMapper;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }
}

