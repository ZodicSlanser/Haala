package com.gizasystems.userservice.util.userCreation;

import com.gizasystems.userservice.converter.UserMapper;
import com.gizasystems.userservice.dto.SignupRequest;
import com.gizasystems.userservice.entity.Role;
import com.gizasystems.userservice.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class OwnerCreationStrategy implements UserCreationStrategy {
    @Override
    public User createUser(SignupRequest signupRequest, Role role, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        return userMapper.toOwner(signupRequest, role, passwordEncoder);
    }
}
