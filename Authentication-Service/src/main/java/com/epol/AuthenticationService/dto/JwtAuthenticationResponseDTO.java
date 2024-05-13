package com.epol.AuthenticationService.dto;

import com.epol.AuthenticationService.models.UserRoles;
import lombok.Data;

@Data
public class JwtAuthenticationResponseDTO {
    private String token;
    private String email;       //Additional
    private String userId;
    private UserRoles userRole;
}
