package com.gizasystems.restaurantservice.DTOs;

public class ItemDto {
    private Long id;
    private String name;
    private String description;
    private double price;
    private double rating;
    private String category;
    private int quantity;
//    private boolean availability;

    public ItemDto(Long id, String name, String description, double price, double rating, String category, int quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.rating = rating;
        this.category = category;
        this.quantity = quantity;
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

    public double getPrice() {
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
}

