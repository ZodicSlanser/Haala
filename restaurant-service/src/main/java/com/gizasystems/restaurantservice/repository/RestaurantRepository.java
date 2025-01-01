package com.gizasystems.restaurantservice.repository;

import com.gizasystems.restaurantservice.entites.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{
}
