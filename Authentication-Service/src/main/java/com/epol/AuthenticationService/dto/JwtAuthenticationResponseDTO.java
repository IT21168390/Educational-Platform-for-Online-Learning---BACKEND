package com.epol.AuthenticationService.dto;

import lombok.Data;

@Data
public class JwtAuthenticationResponseDTO {
    private String token;
    private String email;       //Additional
}
