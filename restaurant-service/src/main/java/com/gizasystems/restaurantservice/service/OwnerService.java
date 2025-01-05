package com.gizasystems.restaurantservice.service;

import com.gizasystems.restaurantservice.DTOs.OwnerDto;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface OwnerService {
    OwnerDto createOwner(OwnerDto ownerDto);
    OwnerDto getOwnerById(Long ownerId);
    List<OwnerDto> getAllOwners();
    OwnerDto updateOwner(Long ownerId, OwnerDto updatedOwner);
    void deleteOwner(Long ownerId);
    List<Long> getRestaurantsByOwnerId(Long ownerId);
}
