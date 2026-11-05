package kh.edu.istad.platform.product.dto;

import build.buf.gen.kh.edu.istad.product.v1.CreateProductResponse;
import build.buf.gen.kh.edu.istad.product.v1.GetProductByIdResponse;

public record ProductResponseDto(
        String id,
        String name,
        double price,
        int quantity
) {
    public static ProductResponseDto fromGrpcResponse(GetProductByIdResponse response) {
        return new ProductResponseDto(
                response.getProductId(),
                response.getName(),
                response.getPrice(),
                response.getQuantity()
        );
    }

    public static ProductResponseDto fromGrpcResponse(CreateProductResponse response) {
        return new ProductResponseDto(
                response.getProductId(),
                response.getName(),
                response.getPrice(),
                response.getQuantity()
        );
    }
}
