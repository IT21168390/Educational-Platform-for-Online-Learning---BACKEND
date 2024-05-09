package edu.epol.CourseManagementService.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
/*import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;*/

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class Configs {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .build();
    }
    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Configure HTTP security using HttpSecurity methods (authorizeRequests, csrf, etc.)
        http
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:5173"));  // Replace with your frontend origin
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));  // Allowed HTTP methods
                    config.setAllowedHeaders(List.of("*"));  // Allowed headers (adjust as needed)
                    return config;
                }));
        // ... other security configurations
        return http.build();
    }*/


}
