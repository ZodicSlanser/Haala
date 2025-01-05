package com.gizasystems.restaurantservice.repository;

import com.gizasystems.restaurantservice.entites.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>{
    List<Item> findByRestaurant_Id(Long restaurantId);  // Note: Using '_id' to match the foreign key column name
    List<Item> findByCategory(String category);
}
