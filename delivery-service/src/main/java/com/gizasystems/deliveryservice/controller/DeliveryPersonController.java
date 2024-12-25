package com.gizasystems.deliveryservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gizasystems.deliveryservice.entity.DeliveryPerson;
import com.gizasystems.deliveryservice.service.DeliveryPersonService;
import com.gizasystems.deliveryservice.dto.UpdateAvailabilityRequest;

@RestController
@RequestMapping("/api/v1/delivery-person")
public class DeliveryPersonController {

  @Autowired
  private DeliveryPersonService deliveryPersonService;

  @PostMapping("/update-availability")
  public ResponseEntity<DeliveryPerson> updateAvailability(@RequestBody UpdateAvailabilityRequest request) {
    DeliveryPerson updatedDeliveryPerson = deliveryPersonService.updateAvailability(request.getDeliveryPersonId(), request.getAvailability());
    return ResponseEntity.ok(updatedDeliveryPerson);
  }
}
