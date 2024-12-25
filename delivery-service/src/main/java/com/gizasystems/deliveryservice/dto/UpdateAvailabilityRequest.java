package com.gizasystems.deliveryservice.dto;

@lombok.Data
public class UpdateAvailabilityRequest {
    private Long deliveryPersonId;
    private Boolean availability;
}

