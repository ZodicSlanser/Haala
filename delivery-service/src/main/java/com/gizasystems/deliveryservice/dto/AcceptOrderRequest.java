package com.gizasystems.deliveryservice.dto;

@lombok.Data
public class AcceptOrderRequest {
    private Long deliveryPersonId;
    private Long orderId;
}
