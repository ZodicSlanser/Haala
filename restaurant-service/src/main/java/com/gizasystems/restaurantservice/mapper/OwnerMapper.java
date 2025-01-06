package com.gizasystems.restaurantservice.mapper;

import com.gizasystems.restaurantservice.DTOs.OwnerDto;
import com.gizasystems.restaurantservice.entites.Owner;

public class OwnerMapper {
    public static OwnerDto mapToOwnerDto(Owner owner) {
        return new OwnerDto(
                owner.getId(),
                owner.getFirstName(),
                owner.getLastName(),
                owner.getEmail(),
                owner.getPassword(),
                owner.getCreatedAt(),
                owner.getPhoneNumber(),
                owner.getRestaurantIds()
        );
    }

    public static Owner mapToOwner(OwnerDto ownerDto) {
        Owner owner = new Owner();
        owner.setId(ownerDto.getId());
        owner.setFirstName(ownerDto.getFirstName());
        owner.setLastName(ownerDto.getLastName());
        owner.setEmail(ownerDto.getEmail());
        owner.setPassword(ownerDto.getPassword());
        owner.setPhoneNumber(ownerDto.getPhoneNumber());
        owner.setRestaurantIds(ownerDto.getRestaurantIds());
        return owner;
    }
}
