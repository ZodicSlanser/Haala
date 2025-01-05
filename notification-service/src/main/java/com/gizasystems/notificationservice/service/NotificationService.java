package com.gizasystems.notificationservice.service;

import com.gizasystems.notificationservice.dto.OrderStatusNotification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

    private final EmailService emailService;

    public void processUserNotification(OrderStatusNotification notification) {
        log.info("Processing user notification for order: {}", notification.orderId());
        emailService.sendUserEmail(notification);
    }

    public void processRestaurantNotification(OrderStatusNotification notification) {
        log.info("Processing restaurant notification for order: {}", notification.orderId());
        emailService.sendRestaurantEmail(notification);
    }
}
