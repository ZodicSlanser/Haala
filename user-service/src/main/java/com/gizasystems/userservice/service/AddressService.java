package com.gizasystems.userservice.service;


import com.gizasystems.userservice.dto.AddressDTO;
import com.gizasystems.userservice.entity.Address;
import com.gizasystems.userservice.entity.Customer;

import com.gizasystems.userservice.repository.AddressRepository;
import com.gizasystems.userservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public AddressDTO createAddress(Long customerId, AddressDTO addressDTO) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        Address address = new Address();
        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setZip(addressDTO.getZip());
        address.setLatitude(addressDTO.getLatitude());
        address.setLongitude(addressDTO.getLongitude());
        address.setCustomer(customer);


        Address savedAddress = addressRepository.save(address);
        return mapToDTO(savedAddress);
    }

    private boolean isAuthorized(Long customerId, Address address) {

        return address.getCustomer().getId().equals(customerId);
    }

    public AddressDTO updateAddress(Long customerId, Long addressId, AddressDTO addressDTO) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));

        if (!isAuthorized(customerId, address)) {
            throw new IllegalArgumentException("Not authorized to update address");
        }

        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setZip(addressDTO.getZip());
        address.setLatitude(addressDTO.getLatitude());
        address.setLongitude(addressDTO.getLongitude());

        Address updatedAddress = addressRepository.save(address);
        return mapToDTO(updatedAddress);
    }

    public void deleteAddress(Long customerId, Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));

        if (!isAuthorized(customerId, address)) {
            throw new IllegalArgumentException("Not authorized to update address");
        }
        addressRepository.deleteById(addressId);
    }

    public List<AddressDTO> getAddressesByCustomer(Long customerId) {
        List<Address> addresses = addressRepository.findByCustomerId(customerId);

        return addresses.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private AddressDTO mapToDTO(Address address) {
        AddressDTO dto = new AddressDTO();
        dto.setStreet(address.getStreet());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setZip(address.getZip());
        dto.setLatitude(address.getLatitude());
        dto.setLongitude(address.getLongitude());
        dto.setId(address.getId());
        return dto;
    }
}
