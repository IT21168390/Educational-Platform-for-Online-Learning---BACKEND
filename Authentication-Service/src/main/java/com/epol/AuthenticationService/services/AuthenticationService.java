package com.epol.AuthenticationService.services;

import com.epol.AuthenticationService.dto.JwtAuthenticationResponseDTO;
import com.epol.AuthenticationService.dto.SignInRequestDTO;
import com.epol.AuthenticationService.dto.SignUpRequestDTO;
import com.epol.AuthenticationService.dto.UserDTO;

public interface AuthenticationService {
    //User signUp(SignUpRequestDTO signUpRequestDTO);
    UserDTO signUp(SignUpRequestDTO signUpRequestDTO);

    JwtAuthenticationResponseDTO signIn(SignInRequestDTO signInRequest);

    boolean emailAlreadyExists(String email);

    boolean validateToken(String token);

}
