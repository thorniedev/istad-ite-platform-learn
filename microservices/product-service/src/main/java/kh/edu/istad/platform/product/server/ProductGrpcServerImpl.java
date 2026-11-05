package kh.edu.istad.platform.product.server;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import kh.edu.istad.platform.product.entity.Product;
import kh.edu.istad.platform.product.service.ProductService;

// === Old version
//import kh.edu.istad.product.grpc.CreateProductRequest;
//import kh.edu.istad.product.grpc.ProductGrpcServiceGrpc;
//import kh.edu.istad.product.grpc.ProductRequest;
//import kh.edu.istad.product.grpc.ProductResponse;

import build.buf.gen.kh.edu.istad.product.v1.CreateProductRequest;
import build.buf.gen.kh.edu.istad.product.v1.CreateProductResponse;
import build.buf.gen.kh.edu.istad.product.v1.GetProductByIdRequest;
import build.buf.gen.kh.edu.istad.product.v1.GetProductByIdResponse;
import build.buf.gen.kh.edu.istad.product.v1.ProductGrpcServiceGrpc;

import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class ProductGrpcServerImpl extends ProductGrpcServiceGrpc.ProductGrpcServiceImplBase{

    private final ProductService productService;

    @Override
    public void getProductById(GetProductByIdRequest request, StreamObserver<GetProductByIdResponse> responseObserver) {

        Product product = productService.findByID(request.getProductId());

        if (product == null) {
            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription("Product with ID " + request.getProductId() + " not found")
                            .asRuntimeException()
            );
            return;
        }

        GetProductByIdResponse response = GetProductByIdResponse.newBuilder()
                .setProductId(product.getId())
                .setName(product.getName())
                .setPrice(product.getPrice())
                .setQuantity(product.getQuantity())
                .build();

        responseObserver.onNext(response); //
        responseObserver.onCompleted(); // end proccess
    }

    // Create Product
    @Override
    public void createProduct(CreateProductRequest request, StreamObserver<CreateProductResponse> responseObserver) {

        Product product = productService.create(
                request.getName(),
                request.getPrice(),
                request.getQuantity()
        );

        CreateProductResponse response = CreateProductResponse.newBuilder()
                .setProductId(product.getId())
                .setName(product.getName())
                .setPrice(product.getPrice())
                .setQuantity(product.getQuantity())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted(); //
    }

}
