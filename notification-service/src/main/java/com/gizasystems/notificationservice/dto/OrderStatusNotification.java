package com.gizasystems.notificationservice.dto;


import java.io.Serializable;
import java.time.LocalDateTime;

public record OrderStatusNotification(Long orderId, Long userId, Long restaurantId, String orderState,
                                      LocalDateTime timestamp, String message) implements Serializable {
}
