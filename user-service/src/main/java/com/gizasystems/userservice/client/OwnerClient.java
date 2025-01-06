package com.gizasystems.userservice.client;

import com.gizasystems.userservice.dto.DeliveryPersonRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "restaurant-service")
public interface OwnerClient {

    @PostMapping("/api/restaurants/owners")
    ResponseEntity<Void> createOwner(@RequestBody Long ownerId);
}
