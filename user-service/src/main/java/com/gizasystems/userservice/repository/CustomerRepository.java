package com.gizasystems.userservice.repository;

import com.gizasystems.userservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
