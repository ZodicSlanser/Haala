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
  private Long id;

  @Column(nullable = false)
  private Boolean availability = false;

  public DeliveryPerson(Long id) {
    this.id = id;
  }
}

