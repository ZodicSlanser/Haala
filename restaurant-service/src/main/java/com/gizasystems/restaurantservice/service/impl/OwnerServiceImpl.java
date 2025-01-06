package com.gizasystems.restaurantservice.service.impl;

import com.gizasystems.restaurantservice.DTOs.OwnerDto;
import com.gizasystems.restaurantservice.entites.Owner;
import com.gizasystems.restaurantservice.exceptions.ResourceNotFoundException;
import com.gizasystems.restaurantservice.mapper.OwnerMapper;
import com.gizasystems.restaurantservice.repository.OwnerRepository;
import com.gizasystems.restaurantservice.service.OwnerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OwnerServiceImpl implements OwnerService {
    @Autowired
    private OwnerRepository ownerRepository;

    @Override
    public OwnerDto createOwner(OwnerDto ownerDto) {
        Owner owner = OwnerMapper.mapToOwner(ownerDto);
        Owner savedOwner = ownerRepository.save(owner);
        return OwnerMapper.mapToOwnerDto(savedOwner);
    }

    @Override
    public OwnerDto getOwnerById(Long ownerId) {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(
                () -> new ResourceNotFoundException("Owner not found with ID: " + ownerId));
        return OwnerMapper.mapToOwnerDto(owner);
    }

    @Override
    public List<OwnerDto> getAllOwners() {
        List<Owner> owners = ownerRepository.findAll();
        return owners.stream()
                .map(OwnerMapper::mapToOwnerDto)
                .collect(Collectors.toList());
    }

    @Override
    public OwnerDto updateOwner(Long ownerId, OwnerDto updatedOwner) {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(
                () -> new ResourceNotFoundException("Owner not found with ID: " + ownerId));
        owner.setFirstName(updatedOwner.getFirstName());
        owner.setLastName(updatedOwner.getLastName());
        owner.setEmail(updatedOwner.getEmail());
        owner.setPassword(updatedOwner.getPassword());
        owner.setPhoneNumber(updatedOwner.getPhoneNumber());
        owner.setRestaurantIds(updatedOwner.getRestaurantIds());
        Owner savedOwner = ownerRepository.save(owner);
        return OwnerMapper.mapToOwnerDto(savedOwner);
    }

    @Override
    public void deleteOwner(Long ownerId) {
        ownerRepository.findById(ownerId).orElseThrow(
                () -> new ResourceNotFoundException("Owner not found with ID: " + ownerId));
        ownerRepository.deleteById(ownerId);
    }

    @Override
    public List<Long> getRestaurantsByOwnerId(Long ownerId) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found"));
        return owner.getRestaurantIds(); // Assuming Owner class has a getRestaurantIds() method
    }
}
