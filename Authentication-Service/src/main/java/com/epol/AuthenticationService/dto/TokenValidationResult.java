package com.epol.AuthenticationService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenValidationResult {
    private boolean valid;
    private String message;
    private String userRole;
    private String userId;
}
