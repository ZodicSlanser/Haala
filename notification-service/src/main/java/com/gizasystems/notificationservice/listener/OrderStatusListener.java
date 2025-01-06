package com.gizasystems.notificationservice.listener;

import com.gizasystems.notificationservice.config.RabbitMQConfig;
import com.gizasystems.notificationservice.dto.OrderStatusNotification;
import com.gizasystems.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@RequiredArgsConstructor
public class OrderStatusListener {

    private final NotificationService notificationService;

    @RabbitListener(queues = RabbitMQConfig.USER_NOTIFICATION_QUEUE)
    public void handleUserNotification(OrderStatusNotification notification) {
        log.info("Received user notification for order: {}", notification.orderId());
        notificationService.processUserNotification(notification);
    }

    @RabbitListener(queues = RabbitMQConfig.RESTAURANT_NOTIFICATION_QUEUE)
    public void handleRestaurantNotification(OrderStatusNotification notification) {
        log.info("Received restaurant notification for order: {}", notification.orderId());
        notificationService.processRestaurantNotification(notification);
    }
}
