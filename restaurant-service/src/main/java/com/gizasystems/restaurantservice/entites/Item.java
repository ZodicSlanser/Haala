package com.gizasystems.restaurantservice.entites;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data

@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private double rating;
    private String category;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    @ToString.Exclude
    @JsonBackReference
    private Restaurant restaurant;  // The relationship with the Restaurant entity

    // Default constructor
    public Item() {
    }


}
