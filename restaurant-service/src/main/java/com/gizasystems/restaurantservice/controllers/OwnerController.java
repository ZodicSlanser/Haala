package com.gizasystems.restaurantservice.controllers;

import com.gizasystems.restaurantservice.DTOs.OwnerDto;
import com.gizasystems.restaurantservice.entites.Owner;
import com.gizasystems.restaurantservice.service.OwnerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gizasystems.restaurantservice.util.helperFunctions.getUserId;

@AllArgsConstructor
@RestController
@RequestMapping("/api/restaurants/owners")
public class OwnerController {
    @Autowired
    private OwnerService ownerService;

    @PostMapping("/assign")
    public ResponseEntity<?> addRestaurantToOwner(@RequestParam Long ownerId, @RequestParam Long restaurantId) {

        ownerService.addRestaurantToOwner(ownerId, restaurantId);
        return ResponseEntity.ok("Restaurant added to owner successfully!");
    }

    @PostMapping()
    public ResponseEntity<Owner> createOwner(@RequestBody Long ownerId) {
        Owner savedOwner = ownerService.createOwner(ownerId);
        return new ResponseEntity<>(savedOwner, HttpStatus.CREATED);
    }


}
