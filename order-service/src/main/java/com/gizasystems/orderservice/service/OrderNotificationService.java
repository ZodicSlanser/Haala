package com.gizasystems.orderservice.service;

import com.gizasystems.orderservice.DTOs.OrderStatusNotification;
import com.gizasystems.orderservice.entites.Order;
import com.gizasystems.orderservice.enums.OrderState;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderNotificationService {

    private static final String ORDER_STATUS_EXCHANGE = "order.status.exchange";
    private static final String ORDER_STATUS_ROUTING_KEY = "order.status.update";
    private final RabbitTemplate rabbitTemplate;

    public void buildAndNotify(Order order, OrderState newState) {
        OrderStatusNotification notification = new OrderStatusNotification();
        notification.setOrderId(order.getId());
        notification.setUserId(order.getCustomerId());
        notification.setRestaurantId(order.getRestaurantId());
        notification.setOrderState(newState.toString());
        notification.setTimestamp(LocalDateTime.now());
        notification.setMessage(generateStatusMessage(order, newState));

        rabbitTemplate.convertAndSend(ORDER_STATUS_EXCHANGE, ORDER_STATUS_ROUTING_KEY, notification);
    }

    private String generateStatusMessage(Order order, OrderState newState) {
        return switch (newState) {
            case PLACED -> "Your order #" + order.getId() + " has been placed successfully";
            case PREPARING -> "Your order #" + order.getId() + " is being prepared";
            case WAITING_FOR_DELIVERY -> "Your order #" + order.getId() + " is ready for pickup";
            case ON_WAY -> "Your order #" + order.getId() + " is out for delivery";
            case DELIVERED -> "Your order #" + order.getId() + " has been delivered";
            case CANCELLED -> "Your order #" + order.getId() + " has been cancelled";
            default -> "Status updated for order #" + order.getId();
        };
    }

}
