package com.novamart.product_service.service;

import com.novamart.product_service.dto.ProductRequest;
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
    public void createProduct(ProductRequest productRequest) {
        List<Reviews> reviews = new ArrayList<>();
        Product product = Product.builder()
                .merchantId(productRequest.merchantId())
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .currencyCode(productRequest.currencyCode())
                .categories(productRequest.categories())
                .reviews(reviews)
                .createdAt(System.currentTimeMillis())
                .updatedAt(System.currentTimeMillis())
                .status("PENDING")
                .attributes(productRequest.attributes())
                .build();
        productRepository.save(product);
        log.info("Product created successfully");
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(String productId) {
        return productRepository.findByProductId(productId);
    }

    public Product getMerchantProducts(String merchantId) {
        return productRepository.findByMerchantId(merchantId);
    }

    public void clearAllProducts() {
        productRepository.deleteAll();
    }
}
