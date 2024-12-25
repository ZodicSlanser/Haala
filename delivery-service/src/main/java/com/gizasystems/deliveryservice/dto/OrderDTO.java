package com.gizasystems.deliveryservice.dto;

import lombok.Data;

@Data
public class OrderDTO {
  private Long id;
  private Long restaurantId;
  private Long customerId;
  private Double total;
  private Double DeliveryFees;
}
