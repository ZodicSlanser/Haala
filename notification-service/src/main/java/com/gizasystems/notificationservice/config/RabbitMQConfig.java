package com.gizasystems.notificationservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String ORDER_STATUS_EXCHANGE = "order.status.exchange";
    public static final String USER_NOTIFICATION_QUEUE = "user.notification.queue";
    public static final String RESTAURANT_NOTIFICATION_QUEUE = "restaurant.notification.queue";
    public static final String ORDER_STATUS_ROUTING_KEY = "order.status.update";

    @Bean
    public DirectExchange orderStatusExchange() {
        return new DirectExchange(ORDER_STATUS_EXCHANGE);
    }

    @Bean
    public Queue userNotificationQueue() {
        return new Queue(USER_NOTIFICATION_QUEUE);
    }

    @Bean
    public Queue restaurantNotificationQueue() {
        return new Queue(RESTAURANT_NOTIFICATION_QUEUE);
    }

    @Bean
    public Binding userBinding(Queue userNotificationQueue, DirectExchange orderStatusExchange) {
        return BindingBuilder.bind(userNotificationQueue)
                .to(orderStatusExchange)
                .with(ORDER_STATUS_ROUTING_KEY);
    }

    @Bean
    public Binding restaurantBinding(Queue restaurantNotificationQueue, DirectExchange orderStatusExchange) {
        return BindingBuilder.bind(restaurantNotificationQueue)
                .to(orderStatusExchange)
                .with(ORDER_STATUS_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return new Jackson2JsonMessageConverter(mapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter());
        return template;
    }

}
