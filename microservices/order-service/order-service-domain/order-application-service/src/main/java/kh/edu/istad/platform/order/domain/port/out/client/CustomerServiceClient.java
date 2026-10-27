package kh.edu.istad.platform.order.domain.port.out.client;

import kh.edu.istad.platform.order.domain.dto.client.CustomerResponse;
import reactor.core.publisher.Flux;

public interface CustomerServiceClient {

    Flux<CustomerResponse> getCustomers();

    default Flux<String> getCustomerNames() {
        return getCustomers().map(CustomerResponse::givenName);
    }

}
