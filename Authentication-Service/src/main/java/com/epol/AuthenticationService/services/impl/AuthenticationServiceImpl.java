package com.epol.AuthenticationService.services.impl;

import com.epol.AuthenticationService.dto.JwtAuthenticationResponseDTO;
import com.epol.AuthenticationService.dto.SignInRequestDTO;
import com.epol.AuthenticationService.dto.SignUpRequestDTO;
import com.epol.AuthenticationService.dto.UserDTO;
import com.epol.AuthenticationService.models.User;
import com.epol.AuthenticationService.models.UserRoles;
import com.epol.AuthenticationService.repositories.UserRepository;
import com.epol.AuthenticationService.services.AuthenticationService;
import com.epol.AuthenticationService.services.JWTService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
        //UserDetails userDetails = userRepository.findByEmail(signInRequest.getEmail());
        //UserDTO userDTO = new UserDTO();
        var jwt = jwtService.generateToken(user);

        JwtAuthenticationResponseDTO jwtAuthenticationResponseDTO = new JwtAuthenticationResponseDTO();
        jwtAuthenticationResponseDTO.setToken(jwt);
        jwtAuthenticationResponseDTO.setEmail(user.getUsername());

        return jwtAuthenticationResponseDTO;
    }

    public boolean validateToken(String token) {
        try {
            jwtService.validateToken(token);
            return true;
        } catch (Exception e) {
            log.warn(e.getMessage());
            return false;
        }
    }

}
