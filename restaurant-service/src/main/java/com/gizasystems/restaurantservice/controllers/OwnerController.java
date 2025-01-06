package com.gizasystems.restaurantservice.controllers;

import com.gizasystems.restaurantservice.DTOs.OwnerDto;
import com.gizasystems.restaurantservice.service.OwnerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/restaurants/owners")
public class OwnerController {
    @Autowired
    private OwnerService ownerService;

    @PostMapping
    public ResponseEntity<OwnerDto> createOwner(@RequestBody OwnerDto ownerDto) {
        OwnerDto savedOwner = ownerService.createOwner(ownerDto);
        return new ResponseEntity<>(savedOwner, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<OwnerDto> getOwnerById(@PathVariable("id") Long ownerId) {
        OwnerDto owner = ownerService.getOwnerById(ownerId);
        return ResponseEntity.ok(owner);
    }

    @GetMapping
    public ResponseEntity<List<OwnerDto>> getAllOwners() {
        List<OwnerDto> owners = ownerService.getAllOwners();
        return ResponseEntity.ok(owners);
    }

    @PutMapping("{id}")
    public ResponseEntity<OwnerDto> updateOwner(@PathVariable("id") Long ownerId, @RequestBody OwnerDto updatedOwner) {
        OwnerDto owner = ownerService.updateOwner(ownerId, updatedOwner);
        return ResponseEntity.ok(owner);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteOwner(@PathVariable("id") Long ownerId) {
        ownerService.deleteOwner(ownerId);
        return ResponseEntity.ok("Owner deleted successfully!");
    }
}
