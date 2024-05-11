package edu.epol.CourseManagementService.configurations;

import edu.epol.CourseManagementService.clients.LearnerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
/*import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;*/
/*import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;*/

import java.util.List;

@Configuration
//@EnableWebSecurity
@RequiredArgsConstructor
public class Configs {
    @Autowired
    private LoadBalancedExchangeFilterFunction filterFunction;
    @Bean
    public WebClient learnerServiceWebClient() {
        return WebClient.builder()
                .baseUrl("http://LearnerService")
                .filter(filterFunction)
                .build();
    }

    @Bean
    public LearnerClient learnerClient() {
        HttpServiceProxyFactory httpServiceProxyFactory
                = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(learnerServiceWebClient()))
                .build();
        return httpServiceProxyFactory.createClient(LearnerClient.class);
    }


    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .build();
    }*/



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
