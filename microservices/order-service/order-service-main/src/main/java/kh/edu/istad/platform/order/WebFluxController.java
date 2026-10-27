package kh.edu.istad.platform.order;

import kh.edu.istad.platform.order.domain.dto.client.CustomerResponse;
import kh.edu.istad.platform.order.domain.port.out.client.CustomerServiceClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequestMapping
public class WebFluxController {

    private final CustomerServiceClient customerServiceClient;

    public WebFluxController(CustomerServiceClient customerServiceClient) {
        this.customerServiceClient = customerServiceClient;
    }

    @GetMapping("/api/customers")
    public Flux<CustomerResponse> getCustomers() {
        return customerServiceClient.getCustomers();
    }

    @GetMapping("/customers")
    public Flux<CustomerResponse> getCustomersLegacy() {
        return customerServiceClient.getCustomers();
    }

    @GetMapping("/flux")
    public Flux<String> getFluxData() {
        return Flux.just("A", "B", "C", "D", "E", "F")
                .log()
                .delayElements(Duration.ofSeconds(1));
    }

}
