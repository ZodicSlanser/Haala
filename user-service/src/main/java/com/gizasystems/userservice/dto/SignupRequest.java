package com.gizasystems.userservice.dto;

import lombok.Data;

import java.util.List;


@Data
public class SignupRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role; // e.g., "CUSTOMER", "OWNER", etc.
    private String phoneNumber;


    // For Delivery Person-specific fields
    private Boolean availability;

    // Additional fields can be added as needed
}
