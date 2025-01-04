package com.gizasystems.restaurantservice.DTOs;

import java.util.List;

public class RestaurantDto {
    private Long id;
    private String name;
    private Long addressId;
    private List<Long> ownerIds;
    private List<Long> itemIds;

    // Constructor with all fields
    public RestaurantDto(Long id, String name, Long addressId, List<Long> ownerIds, List<Long> itemIds) {
        this.id = id;
        this.name = name;
        this.addressId = addressId;
        this.ownerIds = ownerIds;
        this.itemIds = itemIds;
    }

    // Default Constructor
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

    public List<Long> getOwnerIds() {
        return ownerIds;
    }

    public List<Long> getItemIds() {
        return itemIds;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public void setOwnerIds(List<Long> ownerIds) {
        this.ownerIds = ownerIds;
    }

    public void setItemIds(List<Long> itemIds) {
        this.itemIds = itemIds;
    }
}
