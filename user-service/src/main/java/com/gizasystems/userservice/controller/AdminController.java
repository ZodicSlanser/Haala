package com.gizasystems.userservice.controller;


import com.gizasystems.userservice.service.AdminService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gizasystems.userservice.dto.SignupRequest;
import com.gizasystems.userservice.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/create-owner")
    public ResponseEntity<UserDTO> createOwner(@RequestBody SignupRequest signupRequest) {
        UserDTO owner = adminService.createOwnerAccount(signupRequest);
        return ResponseEntity.ok(owner);
    }

    @PostMapping("/create-delivery")
    public ResponseEntity<UserDTO> createDelivery(@RequestBody SignupRequest signupRequest) {
        UserDTO delivery = adminService.createDeliveryAccount(signupRequest);
        return ResponseEntity.ok(delivery);
    }

    @PostMapping("/create-admin")
    public ResponseEntity<UserDTO> createAdmin(@RequestBody SignupRequest signupRequest) {
        UserDTO admin = adminService.createAdminAccount(signupRequest);
        return ResponseEntity.ok(admin);
    }
}
