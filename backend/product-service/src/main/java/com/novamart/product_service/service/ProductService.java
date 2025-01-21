package com.novamart.product_service.service;

import com.novamart.product_service.dto.ProductRequest;
import com.novamart.product_service.model.Product;
import com.novamart.product_service.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.name())
                .product_id(productRequest.product_id())
                .description(productRequest.description())
                .price(productRequest.price())
                .currency_code(productRequest.currency_code())
                .categories(productRequest.categories())
                .reviews(productRequest.reviews())
                .created_at(productRequest.created_at())
                .updated_at(productRequest.updated_at())
                .status(productRequest.status())
                .attributes(productRequest.attributes())
                .build();
        productRepository.save(product);
        log.info("Product created successfully");
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
