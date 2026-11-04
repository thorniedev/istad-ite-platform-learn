package kh.edu.istad.platform.order.client.grpc;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import kh.edu.istad.platform.order.domain.dto.client.QueryProductResult;
import kh.edu.istad.platform.order.domain.port.out.client.ProductServiceClient;
import kh.edu.istad.product.grpc.ProductRequest;
import kh.edu.istad.product.grpc.ProductResponse;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import kh.edu.istad.product.grpc.ProductGrpcServiceGrpc;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;


@Component
@Slf4j
public class ProductGrpcClientAdapter implements ProductServiceClient {

    @GrpcClient("product-service")
    private ProductGrpcServiceGrpc.ProductGrpcServiceBlockingStub productBlockingStub;

//    public ProductResponse findProductById(String productId) {
//        ProductRequest request = ProductRequest.newBuilder()
//                .setProductId(productId)
//                .build();
//        try {
//            return productBlockingStub.getProductById(request);
//        } catch (StatusRuntimeException e) {
//            // handle gRPC errors
//            throw new RuntimeException("gRPC call to product-service failed: " + e.getStatus().getDescription(), e);
//        }
//
//    }


    @Override
    public Mono<QueryProductResult> getProductById(String productId) {
        return Mono.fromCallable(() -> {
                    ProductRequest request = ProductRequest.newBuilder()
                            .setProductId(productId)
                            .build();
                    log.info("Dispatching gRPC request to product-service for ID: {}", productId);
                    ProductResponse response = productBlockingStub.getProductById(request);
                    return new QueryProductResult(
                            response.getProductId(),
                            response.getName(),
                            response.getPrice(),
                            response.getQuantity()
                    );
                })
                .subscribeOn(Schedulers.boundedElastic())
                .onErrorMap(StatusRuntimeException.class, ex ->
                        new RuntimeException("gRPC call failed: " + ex.getStatus().getDescription(), ex)
                );
    }
}
