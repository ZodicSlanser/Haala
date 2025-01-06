package com.gizasystems.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;




@Data
public class SignupRequest {

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @Pattern(regexp = "ADMIN|CUSTOMER|OWNER|DELIVERY_PERSON", message = "Invalid role. Must be one of ADMIN, CUSTOMER, OWNER, DELIVERY_PERSON")
    private String role;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{11,11}$", message = "Invalid phone number format")
    private String phoneNumber;

}
