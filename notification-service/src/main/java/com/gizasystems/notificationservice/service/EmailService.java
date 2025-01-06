package com.gizasystems.notificationservice.service;


import com.gizasystems.notificationservice.dto.OrderStatusNotification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    public void sendUserEmail(OrderStatusNotification notification) {
        log.info("Sending email to user {} for order {}: {}",
                notification.userId(),
                notification.orderId(),
                notification.message()
        );

    }

    public void sendRestaurantEmail(OrderStatusNotification notification) {
        log.info("Sending email to restaurant {} for order {}: {}",
                notification.restaurantId(),
                notification.orderId(),
                notification.message()
        );
    }
}