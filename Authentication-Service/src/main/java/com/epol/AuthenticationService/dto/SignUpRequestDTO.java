package com.epol.AuthenticationService.dto;

import lombok.Data;

@Data
public class SignUpRequestDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
