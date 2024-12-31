package com.gizasystems.deliveryservice.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;

import com.gizasystems.deliveryservice.entity.DeliveryPerson;
import com.gizasystems.deliveryservice.repository.DeliveryPersonRepository;

@Service
public class DeliveryPersonService {

  @Autowired
  private DeliveryPersonRepository deliveryPersonRepository;


  @Transactional
  public DeliveryPerson updateAvailability(Long deliveryPersonId, Boolean availability) {
    DeliveryPerson deliveryPerson = deliveryPersonRepository.findById(deliveryPersonId)
      .orElseThrow(() -> new NoSuchElementException("DeliveryPerson not found with id: " + deliveryPersonId));
    deliveryPerson.setAvailability(availability);
    return deliveryPersonRepository.save(deliveryPerson);
  }
}
