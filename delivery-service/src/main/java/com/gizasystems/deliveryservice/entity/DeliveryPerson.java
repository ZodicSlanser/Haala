package com.gizasystems.deliveryservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "delivery_person")
public class DeliveryPerson {

  @Id
  private Long id;

  @Column(nullable = false)
  private Boolean availability = false;

  public DeliveryPerson() {
  }

  public DeliveryPerson(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public Boolean getAvailability() {
    return availability;
  }

  public void setAvailability(Boolean availability) {
    this.availability = availability;
  }
}

