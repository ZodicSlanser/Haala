package com.gizasystems.deliveryservice.dto;

import java.math.BigDecimal;

public class OrderDTO {
  private Long id;
  private Long restaurantId;
  private Long customerId;
  private BigDecimal total;
  private BigDecimal deliveryFees;

  public OrderDTO() {}

  public OrderDTO(Long id, Long restaurantId, Long customerId, BigDecimal total, BigDecimal deliveryFees) {
    this.id = id;
    this.restaurantId = restaurantId;
    this.customerId = customerId;
    this.total = total;
    this.deliveryFees = deliveryFees;
  }

  public Long getId() {
    return id;
  }

  public Long getRestaurantId() {
    return restaurantId;
  }

  public Long getCustomerId() {
    return customerId;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public BigDecimal getDeliveryFees() {
    return deliveryFees;
  }
}
