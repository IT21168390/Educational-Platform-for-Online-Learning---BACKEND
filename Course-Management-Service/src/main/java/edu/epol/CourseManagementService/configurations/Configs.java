package edu.epol.CourseManagementService.configurations;

import edu.epol.CourseManagementService.clients.LearnerClient;
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

}
