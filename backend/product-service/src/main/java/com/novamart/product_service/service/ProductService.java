package com.novamart.product_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novamart.product_service.client.InventoryClient;
import com.novamart.product_service.client.UserClient;
import com.novamart.product_service.dto.InventoryRequest;
import com.novamart.product_service.dto.InventoryResponse;
import com.novamart.product_service.dto.ProductRequest;
import com.novamart.product_service.dto.ProductResponse;
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

    public void createProduct(ProductRequest productRequest) {
        if (!userClient.authenticateUser(productRequest.merchantId(), "accountType", "MERCHANT")) {
            throw new RuntimeException("User not authenticated");
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

        log.info(inventoryClient.createProductInventory(inventoryRequest));
        productRepository.save(product);
        publishProductUpdate(product);
    }

    public List<ProductResponse> getAllProducts() {
        List<ProductResponse> productResponseList = new ArrayList<>();
        for (Product product : productRepository.findAll()) {
            log.info(product.toString());
            InventoryResponse inventoryResponse = inventoryClient.getInventoryByProductId(product.getProductId());
            List<Reviews> reviews = reviewService.getReviewByProductId(product.getProductId());
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
        return productResponseList;
    }

    public List<ProductResponse> getProduct(String productId) {
        Product product = productRepository.findByProductId(productId);
        InventoryResponse inventoryResponse = inventoryClient.getInventoryByProductId(productId);
        List<Reviews> reviews = reviewService.getReviewByProductId(productId);
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
        return Collections.singletonList(productResponse);
    }

    public List<ProductResponse> getMerchantProducts(String merchantId) {
        List<ProductResponse> productResponseList = new ArrayList<>();
        for (Product product : productRepository.findByMerchantId(merchantId)) {
            InventoryResponse inventoryResponse = inventoryClient.getInventoryByProductId(product.getProductId());
            List<Reviews> reviews = reviewService.getReviewByProductId(product.getProductId());
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
        return productResponseList;
    }

    public void clearAllProducts(String userId) {
        if (!userClient.authenticateUser(userId, "accountType", "ADMIN")) {
            throw new RuntimeException("User not authenticated");
        }
        log.info(inventoryClient.deleteAllInventory());
        reviewService.deleteAllReviews(userId);
        productRepository.deleteAll();
    }

    public void publishProductUpdate(Product product) {
        try {
            kafkaTemplate.send("product", objectMapper.writeValueAsString(product));
        } catch (Exception e) {
            log.error("Error publishing product: {}", e.getMessage());
        }
    }
}
