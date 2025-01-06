package com.gizasystems.userservice.service;

import com.gizasystems.userservice.converter.UserMapper;
import com.gizasystems.userservice.dto.AuthRequest;
import com.gizasystems.userservice.dto.AuthResponse;
import com.gizasystems.userservice.dto.SignupRequest;
import com.gizasystems.userservice.dto.UserDTO;
import com.gizasystems.userservice.entity.*;
import com.gizasystems.userservice.repository.UserRepository;
import com.gizasystems.userservice.util.JwtUtil;
import com.gizasystems.userservice.util.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserFactory userFactory;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public UserService(UserRepository userRepository, UserFactory userFactory, UserMapper userMapper, AuthenticationManager authenticationManager, JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.userFactory = userFactory;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Transactional
    public UserDTO registerUser(SignupRequest signupRequest) {
        // Check if user already exists
        if (userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }
        signupRequest.setRole("CUSTOMER");

        // Delegate user creation to the factory
        User user = userFactory.createUser(signupRequest);

        // Save the user to the database
        userRepository.save(user);

        // Map the User entity to UserDTO
        return userMapper.toDTO(user);
    }

    public AuthResponse authenticate(AuthRequest authRequest) throws Exception {
        // Authenticate the user
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect email or password", e);
        }

        // Load user details
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());

        // Find user in the database
        final User user = userRepository.findByEmail(authRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Generate JWT token
        final String jwt = jwtUtil.generateToken(user.getEmail(), user.getId(), user.getRole().getName());

        // Fetch UserDTO
        UserDTO userDTO = getUserDTOByEmail(authRequest.getUsername());

        // Return AuthResponse
        return new AuthResponse(jwt, userDTO);
    }


    @Transactional
    public UserDTO updateUser(Long userId, SignupRequest signupRequest) {
        // Retrieve the user from the database
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update the user's details
        user.setFirstName(signupRequest.getFirstName());
        user.setLastName(signupRequest.getLastName());
        // Role change is omitted for security reasons, but you can add logic if needed.

        // Save the updated user
        userRepository.save(user);

        // Map the updated user to UserDTO
        return userMapper.toDTO(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        // Check if the user exists
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }

        // Delete the user
        userRepository.deleteById(userId);
    }


    public UserDTO getUserDTOByEmail(String email) {
        // Retrieve the user from the database
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Map the User entity to UserDTO
        return userMapper.toDTO(user);
    }
}
