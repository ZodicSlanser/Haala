package com.gizasystems.restaurantservice.repository;

import com.gizasystems.restaurantservice.entites.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
