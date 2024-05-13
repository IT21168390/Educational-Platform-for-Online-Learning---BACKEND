package com.epol.AuthenticationService.services.impl;

import com.epol.AuthenticationService.dto.*;
import com.epol.AuthenticationService.models.User;
import com.epol.AuthenticationService.models.UserRoles;
import com.epol.AuthenticationService.repositories.UserRepository;
import com.epol.AuthenticationService.services.AuthenticationService;
import com.epol.AuthenticationService.services.JWTService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public UserDTO signUp(SignUpRequestDTO signUpRequestDTO) {
        User user = new User();

        if (signUpRequestDTO.getEmail() == null) {
            throw new IllegalArgumentException("Email is required!");
        } else {
            user.setEmail(signUpRequestDTO.getEmail());
        }
        user.setFirstName(signUpRequestDTO.getFirstName());
        user.setLastName(signUpRequestDTO.getLastName());
        user.setUserRole(UserRoles.STUDENT);
        user.setPassword(passwordEncoder.encode(signUpRequestDTO.getPassword()));

        //return userRepository.save(user);
        User createdUser = userRepository.save(user);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(createdUser.getId());
        userDTO.setLastName(createdUser.getLastName());
        userDTO.setEmail(createdUser.getEmail());
        userDTO.setFirstName(createdUser.getFirstName());
        userDTO.setUserRole(createdUser.getUserRole());

        return userDTO;
    }

    public boolean emailAlreadyExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public JwtAuthenticationResponseDTO signIn(SignInRequestDTO signInRequest) throws IllegalArgumentException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
        var user = userRepository.findByEmail(signInRequest.getEmail());
        var jwt = jwtService.generateToken(user);

        JwtAuthenticationResponseDTO jwtAuthenticationResponseDTO = new JwtAuthenticationResponseDTO();
        jwtAuthenticationResponseDTO.setToken(jwt);
        jwtAuthenticationResponseDTO.setEmail(user.getUsername());
        User userData = userRepository.findFirstByEmail(user.getUsername());
        jwtAuthenticationResponseDTO.setUserId(userData.getId());
        jwtAuthenticationResponseDTO.setUserRole(userData.getUserRole());

        return jwtAuthenticationResponseDTO;
    }

    public TokenValidationResult validateToken(String token) {
        try {
            UserDetails userDetails = jwtService.extractUserDetails(token);

            // Check if the token is expired
            if (jwtService.isTokenExpired(token)) {
                return new TokenValidationResult(false, "Token expired", null, null);
            }

            // Check if the token is valid for the user
            if (!jwtService.isTokenValid(token, userDetails)) {
                return new TokenValidationResult(false, "Invalid token", null, null);
            }

            // Get user role and userId
            String userRole = userDetails.getAuthorities().iterator().next().getAuthority();
            String userId = userDetails.getUsername();

            return new TokenValidationResult(true, "Token is valid", userRole, userId);
        } catch (Exception e) {
            return new TokenValidationResult(false, "Error validating token", null, null);
        }
    }

}
