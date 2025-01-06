package com.gizasystems.userservice.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gizasystems.userservice.dto.SignupRequest;
import com.gizasystems.userservice.dto.UserDTO;
import com.gizasystems.userservice.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", imports = PasswordEncoder.class)
public interface UserMapper {

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(signupRequest.getPassword()))")
    @Mapping(target = "role", source = "role")
    Customer toCustomer(SignupRequest signupRequest, Role role, PasswordEncoder passwordEncoder);

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(signupRequest.getPassword()))")
    @Mapping(target = "role", source = "role")
    Owner toOwner(SignupRequest signupRequest, Role role, PasswordEncoder passwordEncoder);

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(signupRequest.getPassword()))")
    @Mapping(target = "role", source = "role")
    DeliveryPerson toDeliveryPerson(SignupRequest signupRequest, Role role, PasswordEncoder passwordEncoder);

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(signupRequest.getPassword()))")
    @Mapping(target = "role", source = "role")
    Admin toAdmin(SignupRequest signupRequest, Role role, PasswordEncoder passwordEncoder);


    @Mapping(target = "role", source = "role.name") // Convert role object to its name
    UserDTO toDTO(User user);

}
