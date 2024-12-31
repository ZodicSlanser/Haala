package com.gizasystems.deliveryservice.repository;

import com.gizasystems.deliveryservice.entity.DeliveryPerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryPersonRepository extends JpaRepository<DeliveryPerson, Long> {
}

