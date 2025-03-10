package com.novamart.product_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novamart.product_service.client.InventoryClient;
import com.novamart.product_service.client.UserClient;
import com.novamart.product_service.dto.*;
import com.novamart.product_service.model.Product;
import com.novamart.product_service.model.Reviews;
import com.novamart.product_service.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final InventoryClient inventoryClient;
    private final UserClient userClient;
    private final ReviewService reviewService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public ApiResponse createProduct(ProductRequest productRequest) {
        ApiResponse user = userClient.authenticateUser(productRequest.merchantId(), "accountType", "MERCHANT");
        if (user == null || user.status() != 200) {
            return new ApiResponse(401, "User not authenticated", null);
        }
        Product product = Product.builder()
                .productId(UUID.randomUUID().toString())
                .merchantId(productRequest.merchantId())
                .name(productRequest.name())
                .description(productRequest.description())
                .images(productRequest.images())
                .price(productRequest.price())
                .currencyCode(productRequest.currencyCode())
                .categories(productRequest.categories())
                .createdAt(System.currentTimeMillis())
                .updatedAt(System.currentTimeMillis())
                .status("PENDING")
                .attributes(productRequest.attributes())
                .build();
        InventoryRequest inventoryRequest = new InventoryRequest(
                product.getProductId(),
                productRequest.name(),
                productRequest.quantity()
        );

        inventoryClient.createProductInventory(inventoryRequest);
        productRepository.save(product);
        publishProductUpdate(product);

        return new ApiResponse(200, "Product created successfully", null);
    }

    public ApiResponse getAllProducts() {
        List<ProductResponse> productResponseList = new ArrayList<>();
        for (Product product : productRepository.findAll()) {
            InventoryResponse inventoryResponse = (InventoryResponse) inventoryClient.getInventoryByProductId(product.getProductId()).body().getFirst();
            List<Reviews> reviews = (List<Reviews>) reviewService.getReviewByProductId(product.getProductId()).body();
            ProductResponse productResponse = new ProductResponse(
                    product.getProductId(),
                    product.getMerchantId(),
                    product.getName(),
                    product.getImages(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getCurrencyCode(),
                    product.getCategories(),
                    reviews,
                    product.getCreatedAt(),
                    product.getUpdatedAt(),
                    product.getStatus(),
                    product.getAttributes(),
                    inventoryResponse.quantityAvailable(),
                    inventoryResponse.quantitySold(),
                    inventoryResponse.quantityReserved()
            );
            productResponseList.add(productResponse);
        }
        return new ApiResponse(200, "All products fetched", productResponseList);
    }

    public ApiResponse getProduct(String productId) {
        Product product = productRepository.findByProductId(productId);
        InventoryResponse inventoryResponse = (InventoryResponse) inventoryClient.getInventoryByProductId(productId).body().getFirst();
        List<Reviews> reviews = (List<Reviews>) reviewService.getReviewByProductId(productId).body();
        ProductResponse productResponse = new ProductResponse(
                product.getProductId(),
                product.getMerchantId(),
                product.getName(),
                product.getImages(),
                product.getDescription(),
                product.getPrice(),
                product.getCurrencyCode(),
                product.getCategories(),
                reviews,
                product.getCreatedAt(),
                product.getUpdatedAt(),
                product.getStatus(),
                product.getAttributes(),
                inventoryResponse.quantityAvailable(),
                inventoryResponse.quantitySold(),
                inventoryResponse.quantityReserved()
        );
        return new ApiResponse(200, "Product fetched", Collections.singletonList(productResponse));
    }

    public ApiResponse getMerchantProducts(String merchantId) {
        List<ProductResponse> productResponseList = new ArrayList<>();
        for (Product product : productRepository.findByMerchantId(merchantId)) {
            InventoryResponse inventoryResponse = (InventoryResponse) inventoryClient.getInventoryByProductId(product.getProductId()).body().getFirst();
            List<Reviews> reviews = (List<Reviews>) reviewService.getReviewByProductId(product.getProductId()).body();
            ProductResponse productResponse = new ProductResponse(
                    product.getProductId(),
                    product.getMerchantId(),
                    product.getName(),
                    product.getImages(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getCurrencyCode(),
                    product.getCategories(),
                    reviews,
                    product.getCreatedAt(),
                    product.getUpdatedAt(),
                    product.getStatus(),
                    product.getAttributes(),
                    inventoryResponse.quantityAvailable(),
                    inventoryResponse.quantitySold(),
                    inventoryResponse.quantityReserved()
            );
            productResponseList.add(productResponse);
        }
        return new ApiResponse(200, "Merchant products fetched", productResponseList);
    }

    public ApiResponse clearAllProducts(String userId) {
        ApiResponse user = userClient.authenticateUser(userId, "accountType", "ADMIN");
        if (user == null || user.status() != 200) {
            return new ApiResponse(401, "User not authenticated", null);
        }
        inventoryClient.deleteAllInventory();
        reviewService.deleteAllReviews(userId);
        productRepository.deleteAll();

        return new ApiResponse(200, "All products deleted", null);
    }

    public void publishProductUpdate(Product product) {
        try {
            kafkaTemplate.send("product", objectMapper.writeValueAsString(product));
        } catch (Exception e) {
            log.error("Error publishing product: {}", e.getMessage());
        }
    }
}
