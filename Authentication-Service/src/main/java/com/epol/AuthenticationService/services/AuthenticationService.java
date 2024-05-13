package com.epol.AuthenticationService.services;

import com.epol.AuthenticationService.dto.*;

public interface AuthenticationService {
    UserDTO signUp(SignUpRequestDTO signUpRequestDTO);

    JwtAuthenticationResponseDTO signIn(SignInRequestDTO signInRequest);

    boolean emailAlreadyExists(String email);

    TokenValidationResult validateToken(String token);

}
