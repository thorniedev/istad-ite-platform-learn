package kh.edu.istad.platform.order.client.grpc;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import kh.edu.istad.product.grpc.ProductRequest;
import kh.edu.istad.product.grpc.ProductResponse;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import kh.edu.istad.product.grpc.ProductGrpcServiceGrpc;


@Component
public class ProductGrpcClientAdapter {

    @GrpcClient("product-service")
    private ProductGrpcServiceGrpc.ProductGrpcServiceBlockingStub productBlockingStub;

    public ProductResponse findProductById(String productId) {
        ProductRequest request = ProductRequest.newBuilder()
                .setProductId(productId)
                .build();
        try {
            return productBlockingStub.getProductById(request);
        } catch (StatusRuntimeException e) {
            // handle gRPC errors
            throw new RuntimeException("gRPC call to product-service failed: " + e.getStatus().getDescription(), e);
        }

    }


}
