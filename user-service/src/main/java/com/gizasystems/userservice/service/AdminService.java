package com.gizasystems.userservice.service;


import com.gizasystems.userservice.client.DeliveryPersonClient;
import com.gizasystems.userservice.client.OwnerClient;
import com.gizasystems.userservice.converter.UserMapper;
import com.gizasystems.userservice.dto.DeliveryPersonRequest;
import com.gizasystems.userservice.dto.SignupRequest;
import com.gizasystems.userservice.dto.UserDTO;
import com.gizasystems.userservice.entity.User;
import com.gizasystems.userservice.repository.UserRepository;
import com.gizasystems.userservice.util.UserFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final UserFactory userFactory;
    private final UserMapper userMapper;
    private final DeliveryPersonClient deliveryPersonClient;
    private final OwnerClient ownerClient;


    public AdminService(UserRepository userRepository, UserFactory userFactory, UserMapper userMapper, DeliveryPersonClient deliveryPersonClient, OwnerClient ownerClient) {
        this.userRepository = userRepository;
        this.userFactory = userFactory;
        this.userMapper = userMapper;
        this.deliveryPersonClient = deliveryPersonClient;
        this.ownerClient = ownerClient;
    }

    @Transactional
    public UserDTO createOwnerAccount(SignupRequest signupRequest) {
        UserDTO userDTO = createUserWithRole(signupRequest, "OWNER");
        ownerClient.createOwner(userDTO.getId());
        return userDTO;
    }

    @Transactional
    public UserDTO createDeliveryAccount(SignupRequest signupRequest) {

        UserDTO userDTO = createUserWithRole(signupRequest, "DELIVERY_PERSON");


        DeliveryPersonRequest deliveryPersonRequest = new DeliveryPersonRequest();
        deliveryPersonRequest.setId(userDTO.getId());
        deliveryPersonClient.createDeliveryPerson(deliveryPersonRequest);
        return userDTO;
    }

    @Transactional
    public UserDTO createAdminAccount(SignupRequest signupRequest) {
        return createUserWithRole(signupRequest, "ADMIN");
    }

    private UserDTO createUserWithRole(SignupRequest signupRequest, String role) {
        // Check if user already exists
        if (userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        // Set the role explicitly
        signupRequest.setRole(role);

        // Delegate user creation to the factory
        User user = userFactory.createUser(signupRequest);

        // Save the user to the database
        userRepository.save(user);

        // Map the User entity to UserDTO
        return userMapper.toDTO(user);
    }
}
