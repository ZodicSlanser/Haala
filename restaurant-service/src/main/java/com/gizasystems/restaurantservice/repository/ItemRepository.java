package com.gizasystems.restaurantservice.repository;

import com.gizasystems.restaurantservice.entites.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long>{
}
