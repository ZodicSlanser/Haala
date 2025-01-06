package com.gizasystems.restaurantservice.mapper;

import com.gizasystems.restaurantservice.DTOs.OwnerDto;
import com.gizasystems.restaurantservice.entites.Owner;

public class OwnerMapper {
    public static OwnerDto mapToOwnerDto(Owner owner) {
        return new OwnerDto(
                owner.getId()

        );
    }

    public static Owner mapToOwner(OwnerDto ownerDto) {
        Owner owner = new Owner();
        owner.setId(ownerDto.getId());

        return owner;
    }
}
