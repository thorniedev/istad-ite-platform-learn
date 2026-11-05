package kh.edu.istad.platform.order.domain.port.out.client;

import kh.edu.istad.platform.order.domain.dto.client.QueryProductResult;
import reactor.core.publisher.Mono;

public interface ProductServiceClient {
    Mono<java.util.List<QueryProductResult>> getAllProducts();
    Mono<QueryProductResult> getProductById(String productId);
}
