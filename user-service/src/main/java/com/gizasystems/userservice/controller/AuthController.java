package com.gizasystems.userservice.controller;

import com.gizasystems.userservice.dto.AuthRequest;
import com.gizasystems.userservice.dto.AuthResponse;
import com.gizasystems.userservice.dto.SignupRequest;
import com.gizasystems.userservice.dto.UserDTO;
import com.gizasystems.userservice.entity.User;
import com.gizasystems.userservice.repository.UserRepository;
import com.gizasystems.userservice.service.CustomUserDetailsService;
import com.gizasystems.userservice.service.UserService;
import com.gizasystems.userservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;


    private final UserService userService;

    private final UserRepository userRepository;

    @Autowired
    public AuthController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }



    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        // Load user details
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());


        final User user = userRepository.findByEmail(authRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Generate JWT token with email, id, and role
        final String jwt = jwtUtil.generateToken(user.getEmail(), user.getId(), user.getRole().getName());

        // Use UserService to fetch UserDTO
        UserDTO userDTO = userService.getUserDTOByEmail(authRequest.getUsername());

        // Return JWT and UserDTO
        return ResponseEntity.ok(new AuthResponse(jwt, userDTO));
    }


//    @GetMapping("/login")
//    public String login() {
//        return "login";
//    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        try {
            UserDTO newUser = userService.registerUser(signupRequest);
            return ResponseEntity.ok(newUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
