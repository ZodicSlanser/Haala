package com.gizasystems.restaurantservice.DTOs;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDTO {
  private Long id;
  private Long restaurantId;
  private Long customerId;
  private BigDecimal total;
  private BigDecimal deliveryFees;
}
