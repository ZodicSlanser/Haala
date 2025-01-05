package com.gizasystems.userservice.controller;


import com.gizasystems.userservice.dto.AddressDTO;
import com.gizasystems.userservice.service.AddressService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private Long getCustomerPersonId(HttpServletRequest request) {
        try {
            String userIdHeader = request.getHeader("X-User-Id");
            if (userIdHeader != null && !userIdHeader.isEmpty()) {
                return Long.parseLong(userIdHeader);
            }
        } catch (Exception e) {
            throw new RuntimeException("User not found");
        }
        return null;
    }

    final private AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<AddressDTO> createAddress(HttpServletRequest request, @RequestBody AddressDTO addressDTO) {
        Long customerId = getCustomerPersonId(request);
        AddressDTO createdAddress = addressService.createAddress(customerId, addressDTO);
        return ResponseEntity.ok(createdAddress);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(
            @PathVariable Long addressId,
            @RequestBody AddressDTO addressDTO) {
        AddressDTO updatedAddress = addressService.updateAddress(addressId, addressDTO);
        return ResponseEntity.ok(updatedAddress);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<AddressDTO>> getAddressesByCustomer(@PathVariable Long customerId) {
        List<AddressDTO> addresses = addressService.getAddressesByCustomer(customerId);
        return ResponseEntity.ok(addresses);
    }
}
