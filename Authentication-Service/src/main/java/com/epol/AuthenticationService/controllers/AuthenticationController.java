package com.epol.AuthenticationService.controllers;

import com.epol.AuthenticationService.dto.JwtAuthenticationResponseDTO;
import com.epol.AuthenticationService.dto.SignInRequestDTO;
import com.epol.AuthenticationService.dto.SignUpRequestDTO;
import com.epol.AuthenticationService.dto.UserDTO;
import com.epol.AuthenticationService.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/auth")
//@RequiredArgsConstructor
//@CrossOrigin
public class AuthenticationController {
    //private final AuthenticationService authenticationService;
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        if (authenticationService.emailAlreadyExists(signUpRequestDTO.getEmail())) {
            System.out.println("Email already in use!");
            return new ResponseEntity("Email is already used to signup! Try another...", HttpStatus.BAD_REQUEST);
        } else {
            try {
                return ResponseEntity.ok(authenticationService.signUp(signUpRequestDTO));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (ResponseStatusException e) {
                System.out.println(e.getMessage());
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponseDTO> signIn(@RequestBody SignInRequestDTO signInRequestDTO) {
        return ResponseEntity.ok(authenticationService.signIn(signInRequestDTO));
    }

    @GetMapping("/validate")
    public boolean validateToken(@RequestParam String token) {
        return authenticationService.validateToken(token);
    }

}
