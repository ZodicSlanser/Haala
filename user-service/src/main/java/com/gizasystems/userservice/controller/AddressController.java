package com.gizasystems.userservice.controller;


import com.gizasystems.userservice.dto.AddressDTO;
import com.gizasystems.userservice.service.AddressService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
    public ResponseEntity<AddressDTO> createAddress(HttpServletRequest request, @Valid @RequestBody AddressDTO addressDTO) {
        Long customerId = getCustomerPersonId(request);
        AddressDTO createdAddress = addressService.createAddress(customerId, addressDTO);
        return ResponseEntity.ok(createdAddress);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(
            HttpServletRequest request,
            @PathVariable Long addressId,
            @Valid @RequestBody AddressDTO addressDTO) {
        Long customerId = getCustomerPersonId(request);
        AddressDTO updatedAddress = addressService.updateAddress(customerId, addressId, addressDTO);
        return ResponseEntity.ok(updatedAddress);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(
            HttpServletRequest request,
            @PathVariable Long addressId) {
        Long customerId = getCustomerPersonId(request);
        addressService.deleteAddress(customerId, addressId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/my-addresses")
    public ResponseEntity<List<AddressDTO>> getAddressesByCustomer(HttpServletRequest request) {
        Long customerId = getCustomerPersonId(request);
        List<AddressDTO> addresses = addressService.getAddressesByCustomer(customerId);
        return ResponseEntity.ok(addresses);
    }
}
