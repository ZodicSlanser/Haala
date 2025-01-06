package com.gizasystems.restaurantservice.DTOs;

import java.util.List;

public class RestaurantDto {
    private Long id;
    private String name;
    private Long addressId;
    private List<Long> ownersIds;
    private List<Long> itemsIds;

    // Constructor with all fields
    public RestaurantDto(Long id, String name, Long addressId, List<Long> ownersIds, List<Long> itemsIds) {
        this.id = id;
        this.name = name;
        this.addressId = addressId;
        this.ownersIds = ownersIds;
        this.itemsIds = itemsIds;
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

    public List<Long> getOwnersIds() {
        return ownersIds;
    }

    public List<Long> getItemsIds() {
        return itemsIds;
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
        this.ownersIds = ownerIds;
    }

    public void setItemIds(List<Long> itemIds) {
        this.itemsIds = itemIds;
    }
}
