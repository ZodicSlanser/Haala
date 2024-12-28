package com.gizasystems.orderservice.DTOs;

import com.gizasystems.orderservice.enums.OrderState;

public record OrderSearchCriteria(Long restaurantId, OrderState state, Long customerId, Long deliveryId) {
}