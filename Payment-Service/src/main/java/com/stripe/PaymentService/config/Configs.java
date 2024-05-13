package com.stripe.PaymentService.config;


import com.stripe.PaymentService.clients.EnrollmentClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;


@Configuration
@RequiredArgsConstructor
public class Configs {
    @Autowired
    private LoadBalancedExchangeFilterFunction filterFunction;

    @Bean
    public WebClient enrollmentServiceWebClient() {
        return WebClient.builder()
                .baseUrl("http://LearnerService")
                .filter(filterFunction)
                .build();
    }

    @Bean
    public EnrollmentClient enrollmentClient() {
        HttpServiceProxyFactory httpServiceProxyFactory
                = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(enrollmentServiceWebClient()))
                .build();
        return httpServiceProxyFactory.createClient(EnrollmentClient.class);
    }

}