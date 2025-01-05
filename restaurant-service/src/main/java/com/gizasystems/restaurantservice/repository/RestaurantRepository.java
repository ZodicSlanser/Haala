package com.gizasystems.restaurantservice.repository;

import com.gizasystems.restaurantservice.entites.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{
    Optional<Restaurant> findByAddressId(Long addressId);

    @Query("SELECT r FROM Restaurant r WHERE :ownerId MEMBER OF r.ownerIds")
    Optional<Restaurant> findByOwnerId(@Param("ownerId") Long ownerId);

    Optional<Restaurant> findByName(String name);

    Optional<Restaurant> findById(Long restaurantId);
}
