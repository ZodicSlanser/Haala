package com.gizasystems.userservice.controller;

import com.gizasystems.userservice.dto.SignupRequest;
import com.gizasystems.userservice.dto.UserDTO;
import com.gizasystems.userservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    private Long getCustomerPersonId(HttpServletRequest request) {
        try {
            String userIdHeader = request.getHeader("X-User-Id");
            if (userIdHeader != null && !userIdHeader.isEmpty()) {
                return Long.parseLong(userIdHeader);
            }
        } catch (Exception e) {
            throw new RuntimeException("User not found");
        }
        return null;
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateUser(HttpServletRequest request, @Valid @RequestBody SignupRequest signupRequest) {
        Long id = getCustomerPersonId(request);

        UserDTO updatedUser = userService.updateUser(id, signupRequest);
        return ResponseEntity.ok(updatedUser);

    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(HttpServletRequest request) {
        Long id = getCustomerPersonId(request);
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");

    }
}
