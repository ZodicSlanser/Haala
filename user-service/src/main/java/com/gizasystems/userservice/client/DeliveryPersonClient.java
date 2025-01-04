package com.gizasystems.userservice.client;

import com.gizasystems.userservice.dto.DeliveryPersonRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "delivery-service")
public interface DeliveryPersonClient {

    @PostMapping("/api/delivery-person/register")
    ResponseEntity<Void> createDeliveryPerson(@RequestBody DeliveryPersonRequest request);
}
