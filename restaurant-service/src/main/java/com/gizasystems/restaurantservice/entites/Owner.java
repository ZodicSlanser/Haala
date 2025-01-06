package com.gizasystems.restaurantservice.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data

@NoArgsConstructor
@AllArgsConstructor
public class Owner {

    @Id
    Long Id;

    @ManyToMany
    List<Restaurant> restaurants;
}
