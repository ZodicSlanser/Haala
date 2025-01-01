package com.gizasystems.restaurantservice.entites;

import jakarta.persistence.*;

@Entity
@Table(name = "Restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long addressId; // Updated field name

    // Constructor
    public Restaurant(Long id, String name, Long addressId) {
        this.id = id;
        this.name = name;
        this.addressId = addressId;
    }

    // Default Constructor
    public Restaurant() {
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
}
