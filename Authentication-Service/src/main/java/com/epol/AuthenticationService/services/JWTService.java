package com.epol.AuthenticationService.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
    String extractUserName(String token);

    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);

    void validateToken(final String token);
    boolean isTokenExpired(String token);
    UserDetails extractUserDetails(String token);
}
