package com.gizasystems.userservice.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;


import com.gizasystems.userservice.dto.AddressDTO;
import com.gizasystems.userservice.entity.Address;
import com.gizasystems.userservice.entity.Customer;
import com.gizasystems.userservice.repository.AddressRepository;
import com.gizasystems.userservice.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AddressServiceTest {

    @InjectMocks
    private AddressService addressService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AddressRepository addressRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAddress_Success() {
        // Arrange
        Long customerId = 1L;
        Customer mockCustomer = new Customer();
        mockCustomer.setId(customerId);

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet("123 Main St");
        addressDTO.setCity("New York");
        addressDTO.setState("NY");
        addressDTO.setZip("10001");
        addressDTO.setLatitude(40.7128);
        addressDTO.setLongitude(-74.0060);

        Address savedAddress = new Address();
        savedAddress.setId(1L);
        savedAddress.setStreet(addressDTO.getStreet());
        savedAddress.setCity(addressDTO.getCity());
        savedAddress.setState(addressDTO.getState());
        savedAddress.setZip(addressDTO.getZip());
        savedAddress.setLatitude(addressDTO.getLatitude());
        savedAddress.setLongitude(addressDTO.getLongitude());

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(mockCustomer));
        when(addressRepository.save(any(Address.class))).thenReturn(savedAddress);

        // Act
        AddressDTO result = addressService.createAddress(customerId, addressDTO);

        // Assert
        assertNotNull(result);
        assertEquals("123 Main St", result.getStreet());
        assertEquals("New York", result.getCity());
        assertEquals("NY", result.getState());
        assertEquals("10001", result.getZip());
    }

    @Test
    void testCreateAddress_CustomerNotFound() {
        // Arrange
        Long customerId = 1L;
        AddressDTO addressDTO = new AddressDTO();

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            addressService.createAddress(customerId, addressDTO);
        });

        assertEquals("Customer not found", exception.getMessage());
    }
}
