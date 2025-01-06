package com.gizasystems.restaurantservice.service;

import com.gizasystems.restaurantservice.DTOs.OwnerDto;
import com.gizasystems.restaurantservice.entites.Owner;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface OwnerService {


    void addRestaurantToOwner(Long ownerId, Long restaurantId);

    Owner createOwner(Long ownerId);
}
