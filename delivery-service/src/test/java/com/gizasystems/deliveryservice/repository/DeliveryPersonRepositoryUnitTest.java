package com.gizasystems.deliveryservice.repository;

import com.gizasystems.deliveryservice.entity.DeliveryPerson;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DeliveryPersonRepositoryUnitTest {

  @Autowired
  private DeliveryPersonRepository deliveryPersonRepository;

  @Test
  @DisplayName("Save DeliveryPerson")
  @Order(1)
  @Rollback(false)
  public void saveDeliveryPerson() {
    DeliveryPerson deliveryPerson = new DeliveryPerson(1L);
    deliveryPersonRepository.save(deliveryPerson);
    Assertions.assertThat(deliveryPerson.getId()).isNotNull();
    Assertions.assertThat(deliveryPerson.getAvailability()).isFalse();
    Assertions.assertThat(deliveryPerson.getId()).isEqualTo(1L);
  }

  @Test
  @DisplayName("Find DeliveryPerson By Id")
  @Order(2)
  public void findDeliveryPersonById() {
    DeliveryPerson deliveryPerson = deliveryPersonRepository.findById(1L).orElse(null);
    Assertions.assertThat(deliveryPerson).isNotNull();
    Assertions.assertThat(deliveryPerson.getId()).isEqualTo(1L);
  }

  @Test
  @DisplayName("Update DeliveryPerson Availability")
  @Order(3)
  @Rollback(false)
  public void updateDeliveryPersonAvailability() {
    DeliveryPerson deliveryPerson = deliveryPersonRepository.findById(1L).orElse(null);
    Assertions.assertThat(deliveryPerson).isNotNull();
    deliveryPerson.setAvailability(true);
    deliveryPersonRepository.save(deliveryPerson);
    DeliveryPerson updatedDeliveryPerson = deliveryPersonRepository.findById(1L).orElse(null);
    Assertions.assertThat(updatedDeliveryPerson).isNotNull();
    Assertions.assertThat(updatedDeliveryPerson.getAvailability()).isTrue();
  }

}

