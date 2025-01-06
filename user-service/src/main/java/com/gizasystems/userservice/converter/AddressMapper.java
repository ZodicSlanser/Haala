package com.gizasystems.userservice.converter;


import com.gizasystems.userservice.dto.AddressDTO;
import com.gizasystems.userservice.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    // Map AddressDTO to Address entity
    @Mapping(target = "customer", ignore = true) // Customer will be set later
    Address toEntity(AddressDTO addressDTO);

    // Map Address entity to AddressDTO
    AddressDTO toDTO(Address address);

    // Handle collections
    List<Address> toEntityList(List<AddressDTO> addressDTOs);
    List<AddressDTO> toDTOList(List<Address> addresses);
}