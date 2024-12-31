package com.gizasystems.deliveryservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "delivery_person")
@Data
@NoArgsConstructor
public class DeliveryPerson {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Boolean availability = true;
}

