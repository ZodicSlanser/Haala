package com.gizasystems.deliveryservice.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gizasystems.deliveryservice.entity.DeliveryPerson;
import com.gizasystems.deliveryservice.dto.DeliveryPersonDTO;
import com.gizasystems.deliveryservice.repository.DeliveryPersonRepository;
import com.gizasystems.deliveryservice.exception.DeliveryPersonNotFoundException;
import com.gizasystems.deliveryservice.exception.DeliveryPersonAlreadyExistsException;

@Service
public class DeliveryPersonService {

  @Autowired
  private DeliveryPersonRepository deliveryPersonRepository;

  @Transactional
  public DeliveryPerson register(DeliveryPersonDTO deliveryPersonDTO) {
    Long id = deliveryPersonDTO.getId();
    DeliveryPerson dp = deliveryPersonRepository.findById(id).orElse(null);
    if (dp != null) throw new DeliveryPersonAlreadyExistsException("DeliveryPerson already exists with id: " + id);
    return deliveryPersonRepository.save(new DeliveryPerson(id));
  }

  @Transactional
  public DeliveryPerson updateAvailability(Long deliveryPersonId, Boolean availability) {
    DeliveryPerson deliveryPerson = deliveryPersonRepository.findById(deliveryPersonId)
      .orElseThrow(() -> new DeliveryPersonNotFoundException("DeliveryPerson not found with id: " + deliveryPersonId));
    deliveryPerson.setAvailability(availability);
    return deliveryPersonRepository.save(deliveryPerson);
  }
}
