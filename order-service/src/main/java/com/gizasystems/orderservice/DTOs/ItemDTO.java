package com.gizasystems.orderservice.DTOs;


import java.math.BigDecimal;

public record ItemDTO(Long id, String name, String description, String category, BigDecimal price, int quantity,
                      String comments) {
}
