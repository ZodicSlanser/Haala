package com.gizasystems.restaurantservice.DTOs;

import java.math.BigDecimal;

public class ItemDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private double rating;
    private String category;
    private int quantity;
//    private boolean availability;
    private Long restaurantId;

    public ItemDto(Long id, String name, String description, BigDecimal price, double rating, String category, int quantity, Long restaurantId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.rating = rating;
        this.category = category;
        this.quantity = quantity;
        this.restaurantId = restaurantId;
    }
    public ItemDto() {
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public double getRating() {
        return rating;
    }

    public String getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

}

