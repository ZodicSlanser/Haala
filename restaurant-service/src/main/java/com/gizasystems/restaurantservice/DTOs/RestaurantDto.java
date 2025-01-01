package com.gizasystems.restaurantservice.DTOs;

public class RestaurantDto {
    private Long id;
    private String name;
    private Long addressId;

    public RestaurantDto(Long id, String name, Long addressId) {
        this.id = id;
        this.name = name;
        this.addressId = addressId;
    }
    public RestaurantDto() {
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getAddressId() {
        return addressId;
    }

}

