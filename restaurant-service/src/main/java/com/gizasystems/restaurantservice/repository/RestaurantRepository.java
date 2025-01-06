package com.gizasystems.restaurantservice.repository;

import com.gizasystems.restaurantservice.entites.Owner;
import com.gizasystems.restaurantservice.entites.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{

    @Query("SELECT r FROM Restaurant r JOIN r.owners o WHERE o IN :owners")
    List<Restaurant> findRestaurantsByOwners(@Param("owners") List<Owner> owners);

    Optional<Restaurant> findByName(String name);

    Optional<Restaurant> findById(Long restaurantId);
}
