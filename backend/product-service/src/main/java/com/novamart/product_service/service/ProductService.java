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
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .currency_code(productRequest.currency_code())
                .categories(productRequest.categories())
                .reviews(reviews)
                .created_at(System.currentTimeMillis())
                .updated_at(System.currentTimeMillis())
                .status("PENDING")
                .attributes(productRequest.attributes())
                .build();
        productRepository.save(product);
        log.info("Product created successfully");
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(String product_id) {
        return productRepository.findByProductId(product_id);
    }

    public void clearAllProducts() {
        productRepository.deleteAll();
    }
}
