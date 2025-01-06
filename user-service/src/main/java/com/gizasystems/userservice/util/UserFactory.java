package com.gizasystems.userservice.util;

import com.gizasystems.userservice.converter.UserMapper;
import com.gizasystems.userservice.dto.SignupRequest;
import com.gizasystems.userservice.entity.Role;
import com.gizasystems.userservice.entity.User;
import com.gizasystems.userservice.repository.RoleRepository;
import com.gizasystems.userservice.util.userCreation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserFactory {

    private final Map<String, UserCreationStrategy> strategies = new HashMap<>();
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserFactory(List<UserCreationStrategy> strategyList, RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;

        // Populate the strategy map with role-strategy mappings
        strategyList.forEach(strategy -> {
            if (strategy instanceof CustomerCreationStrategy) {
                strategies.put("CUSTOMER", strategy);
            } else if (strategy instanceof OwnerCreationStrategy) {
                strategies.put("OWNER", strategy);
            } else if (strategy instanceof DeliveryPersonCreationStrategy) {
                strategies.put("DELIVERY_PERSON", strategy);
            } else if (strategy instanceof AdminCreationStrategy) {
                strategies.put("ADMIN", strategy);
            }
        });
    }

    public User createUser(SignupRequest signupRequest) {
        String roleName = signupRequest.getRole();
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Invalid role: " + roleName));

        UserCreationStrategy strategy = strategies.get(roleName);
        if (strategy == null) {
            throw new RuntimeException("Unsupported role: " + roleName);
        }

        return strategy.createUser(signupRequest, role, passwordEncoder, userMapper);
    }
}
