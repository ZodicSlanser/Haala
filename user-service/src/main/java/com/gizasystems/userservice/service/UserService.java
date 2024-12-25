package com.gizasystems.userservice.service;

import com.gizasystems.userservice.converter.UserMapper;
import com.gizasystems.userservice.dto.SignupRequest;
import com.gizasystems.userservice.dto.UserDTO;
import com.gizasystems.userservice.entity.*;
import com.gizasystems.userservice.repository.UserRepository;
import com.gizasystems.userservice.util.UserFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserFactory userFactory;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserFactory userFactory, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userFactory = userFactory;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserDTO registerUser(SignupRequest signupRequest) {
        // Check if user already exists
        if (userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        // Delegate user creation to the factory
        User user = userFactory.createUser(signupRequest);

        // Save the user to the database
        userRepository.save(user);

        // Map the User entity to UserDTO
        return userMapper.toDTO(user);
    }


    public UserDTO getUserDTOByEmail(String email) {
        // Retrieve the user from the database
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Map the User entity to UserDTO
        return userMapper.toDTO(user);
    }
}
