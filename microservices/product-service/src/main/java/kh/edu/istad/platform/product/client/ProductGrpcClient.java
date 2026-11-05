package kh.edu.istad.platform.product.client;

//import kh.edu.istad.product.grpc.CreateProductRequest;
//import kh.edu.istad.product.grpc.ProductGrpcServiceGrpc;
//import kh.edu.istad.product.grpc.ProductRequest;
//import kh.edu.istad.product.grpc.ProductResponse;

import build.buf.gen.kh.edu.istad.product.v1.CreateProductRequest;
import build.buf.gen.kh.edu.istad.product.v1.CreateProductResponse;
import build.buf.gen.kh.edu.istad.product.v1.GetProductByIdRequest;
import build.buf.gen.kh.edu.istad.product.v1.GetProductByIdResponse;
import build.buf.gen.kh.edu.istad.product.v1.ProductGrpcServiceGrpc;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class ProductGrpcClient {

    @GrpcClient("product-service")
    private ProductGrpcServiceGrpc.ProductGrpcServiceBlockingStub productBlockingStub;

    public GetProductByIdResponse getProductById(String productID) {
        GetProductByIdRequest request = GetProductByIdRequest.newBuilder()
                .setProductId(productID)
                .build();

        return productBlockingStub.getProductById(request);
    }

    public CreateProductResponse createProduct(String name, double price, int quantity) {
        CreateProductRequest request = CreateProductRequest.newBuilder()
                .setName(name)
                .setPrice(price)
                .setQuantity(quantity)
                .build();

        return productBlockingStub.createProduct(request);
    }
}
