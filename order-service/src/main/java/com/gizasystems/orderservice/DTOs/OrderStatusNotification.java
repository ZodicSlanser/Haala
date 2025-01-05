package com.gizasystems.orderservice.DTOs;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class OrderStatusNotification implements Serializable {
    Long orderId;
    Long userId;
    Long restaurantId;
    String orderState;
    LocalDateTime timestamp;
    String message;
}

