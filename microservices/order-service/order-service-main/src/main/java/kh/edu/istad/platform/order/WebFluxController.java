package kh.edu.istad.platform.order;

import kh.edu.istad.platform.order.config.ServicePropConfig;
import kh.edu.istad.platform.order.domain.dto.client.CustomerResponse;
import kh.edu.istad.platform.order.domain.port.out.client.CustomerServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

@RestController
@RequestMapping
//@RefreshScope
public class WebFluxController {

    private final CustomerServiceClient customerServiceClient;

    private final ServicePropConfig servicePropConfig;

    public WebFluxController(CustomerServiceClient customerServiceClient, ServicePropConfig servicePropConfig) {
        this.customerServiceClient = customerServiceClient;
        this.servicePropConfig = servicePropConfig;
    }

//    @Value("${service.message:Default Message}")
//    private String serviceMessage;
//
//    @Value("${service.version:1.0.0}")
//    private String version;
//
//    @Value("${service.env:local}")
//    private String env;

    @GetMapping("/config-check")
    public Mono< Map<String, String> > getConfig() {
        return Mono.just(
                Map.of(
                        "serviceMessage", servicePropConfig.getMessage(),
                        "version", servicePropConfig.getVersion(),
                        "env", servicePropConfig.getEnv(),
                        "baseUrl", servicePropConfig.getBaseUrl()
                )
        );
    }

    static class WebFluxControllerService {

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
