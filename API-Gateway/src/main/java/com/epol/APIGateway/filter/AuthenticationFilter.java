package com.epol.APIGateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;
    @Autowired
    private RestTemplate template;

    public AuthenticationFilter() {
        super(Config.class);
    }

    ResponseEntity<TokenValidationResult> responseEntity;

    HttpEntity<String> requestEntity = new HttpEntity<>(null, null);

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            /*if (validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Authorization Header is missing!");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader!=null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }

                try {
                    System.out.println(authHeader);

                    UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost:8082/api/v1/auth/validate")
                            .queryParam("token", authHeader)
                            .build();

                    TokenValidationResult responseEntity = template
                            .exchange(uriBuilder.toUriString(),
                                    HttpMethod.GET,
                                    requestEntity,
                                    TokenValidationResult.class).getBody();

                    System.out.println(responseEntity.getUserId()+" "+responseEntity.getUserRole());
                if (!responseEntity.isValid()) {
                    throw new RuntimeException("Probably an invalid token.");
                }
                    //exchange.getRequest().mutate().header("User-Role", responseEntity.getUserRole()).build();

                } catch (Exception e) {
                    throw new RuntimeException("Error during token validation: " + e.getMessage());
                }

            }*/
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}
