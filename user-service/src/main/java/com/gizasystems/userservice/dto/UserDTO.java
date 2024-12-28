package com.gizasystems.userservice.dto;


import lombok.Data;

@Data
public class UserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String phoneNumber;
}
