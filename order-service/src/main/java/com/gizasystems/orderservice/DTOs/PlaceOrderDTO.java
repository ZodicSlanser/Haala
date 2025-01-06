package com.gizasystems.orderservice.DTOs;

import java.util.List;


public record PlaceOrderDTO(Long userId, Long restaurantId, List<ItemDTO> items) {
}
