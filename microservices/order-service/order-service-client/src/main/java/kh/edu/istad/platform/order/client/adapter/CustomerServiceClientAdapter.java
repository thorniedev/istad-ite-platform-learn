package kh.edu.istad.platform.order.client.adapter;

import kh.edu.istad.platform.order.domain.dto.client.CustomerResponse;
import kh.edu.istad.platform.order.domain.port.out.client.CustomerServiceClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
public class CustomerServiceClientAdapter implements CustomerServiceClient {

    private final WebClient webClient;

    public CustomerServiceClientAdapter(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://customer-service")
                .build();
    }

    @Override
    public Flux<CustomerResponse> getCustomers() {
        return webClient
                .get()
                .uri("/api/customers")
                .retrieve()
                .bodyToFlux(CustomerResponse.class);
    }
}
