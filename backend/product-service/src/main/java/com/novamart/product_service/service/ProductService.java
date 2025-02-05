package com.novamart.product_service.service;

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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final InventoryClient inventoryClient;
    private final UserClient userClient;
    private final ReviewService reviewService;

    public void createProduct(ProductRequest productRequest) {
        if (!userClient.authenticateUser(productRequest.merchantId(), "accountType", "MERCHANT")) {
            throw new RuntimeException("User not authenticated");
        }
        List<Reviews> reviews = new ArrayList<>();
        Product product = Product.builder()
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
                productRequest.productId(),
                productRequest.name(),
                productRequest.quantity()
        );

        log.info(inventoryClient.createProductInventory(inventoryRequest));
        productRepository.save(product);
        log.info("Product created successfully");
    }

    public List<ProductResponse> getAllProducts() {
        List<ProductResponse> productResponseList = new ArrayList<>();
        for (Product product : productRepository.findAll()) {
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

    public ProductResponse getProduct(String productId) {
        Product product = productRepository.findByProductId(productId);
        InventoryResponse inventoryResponse = inventoryClient.getInventoryByProductId(productId);
        List<Reviews> reviews = reviewService.getReviewByProductId(productId);
        return new ProductResponse(
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
}
