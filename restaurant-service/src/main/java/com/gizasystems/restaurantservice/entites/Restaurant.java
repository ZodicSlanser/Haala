package com.gizasystems.restaurantservice.entites;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long addressId;

    @ElementCollection
    @CollectionTable(name = "restaurant_owners", joinColumns = @JoinColumn(name = "restaurant_id"))
    @Column(name = "owner_id")
    private List<Long> ownerIds;

    @ElementCollection
    @CollectionTable(name = "restaurant_items", joinColumns = @JoinColumn(name = "restaurant_id"))
    @Column(name = "item_id")
    private List<Long> itemIds; // List of item IDs

    // Constructor
    public Restaurant(Long id, String name, Long addressId, List<Long> ownerIds, List<Long> itemIds) {
        this.id = id;
        this.name = name;
        this.addressId = addressId;
        this.ownerIds = ownerIds;
        this.itemIds = itemIds;
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
