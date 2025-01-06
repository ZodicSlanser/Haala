package com.gizasystems.userservice.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class UserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String phoneNumber;
    private Long id;
}
