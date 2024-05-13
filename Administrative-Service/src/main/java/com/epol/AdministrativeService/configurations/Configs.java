package com.epol.AdministrativeService.configurations;

import com.epol.AdministrativeService.clients.CourseClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;


@Configuration
//@EnableWebSecurity
@RequiredArgsConstructor
public class Configs {
    @Autowired
    private LoadBalancedExchangeFilterFunction filterFunction;
    @Bean
    public WebClient courseServiceWebClient() {
        return WebClient.builder()
                .baseUrl("http://LearnerService")
                .filter(filterFunction)
                .build();
    }

    @Bean
    public CourseClient courseClient() {
        HttpServiceProxyFactory httpServiceProxyFactory
                = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(courseServiceWebClient()))
                .build();
        return httpServiceProxyFactory.createClient(CourseClient.class);
    }


}
