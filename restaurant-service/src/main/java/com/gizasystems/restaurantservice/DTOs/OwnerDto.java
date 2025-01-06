package com.gizasystems.restaurantservice.DTOs;

import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

public class OwnerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private String phoneNumber;
    private List<Long> restaurantIds;

    // Constructors
    public OwnerDto() {
    }

    public OwnerDto(Long id, String firstName, String lastName, String email, String password, LocalDateTime createdAt, String phoneNumber, List<Long> restaurantIds) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.phoneNumber = phoneNumber;
        this.restaurantIds = restaurantIds;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Long> getRestaurantIds() {
        return restaurantIds;
    }

    public void setRestaurantIds(List<Long> restaurantIds) {
        this.restaurantIds = restaurantIds;
    }
}
