package com.novamart.search_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novamart.search_service.dto.ApiResponse;
import com.novamart.search_service.dto.ProductRequest;
import com.novamart.search_service.model.Product;
import com.novamart.search_service.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "product", groupId = "product")
    public void consumeProduct(String product) {
        try {
            Product productModel = objectMapper.readValue(product, Product.class);
            productRepository.save(productModel);
        } catch (Exception e) {
            log.error("Error consuming product: {}", e.getMessage());
        }
    }

    public ApiResponse searchProducts(ProductRequest productRequest) {
        List<Product> products;
        if (productRequest.name() == null) {
            if (productRequest.currencyCode() == null) {
                if (productRequest.categories() == null) {
                    if (productRequest.attributes() == null) {
                        return new ApiResponse(400, "Bad Request", null);
                    } else {
                        products = productRepository.searchByColorAttribute(productRequest.attributes());
                    }
                } else {
                    if (productRequest.attributes() == null) {
                        products = productRepository.searchByCategories(productRequest.categories());
                    } else {
                        products = productRepository.searchByCategoriesAndColorAttribute(productRequest.categories(), productRequest.attributes());
                    }
                }
            } else {
                if (productRequest.categories() == null) {
                    if (productRequest.attributes() == null) {
                        products = productRepository.searchByCurrencyCode(productRequest.currencyCode());
                    } else {
                        products = productRepository.searchByCurrencyCodeAndColorAttribute(productRequest.currencyCode(), productRequest.attributes());
                    }
                } else {
                    if (productRequest.attributes() == null) {
                        products = productRepository.searchByCurrencyCodeAndCategories(productRequest.currencyCode(), productRequest.categories());
                    } else {
                        products = productRepository.searchByCurrencyCodeCategoriesAndColorAttribute(productRequest.currencyCode(), productRequest.categories(), productRequest.attributes());
                    }
                }
            }
        } else {
            if (productRequest.currencyCode() == null) {
                if (productRequest.categories() == null) {
                    if (productRequest.attributes() == null) {
                        products = productRepository.searchByName(productRequest.name());
                    } else {
                        products = productRepository.searchByNameAndColorAttribute(productRequest.name(), productRequest.attributes());
                    }
                } else {
                    if (productRequest.attributes() == null) {
                        products = productRepository.searchByNameAndCategories(productRequest.name(), productRequest.categories());
                    } else {
                        products = productRepository.searchByNameCategoriesAndColorAttribute(productRequest.name(), productRequest.categories(), productRequest.attributes());
                    }
                }
            } else {
                if (productRequest.categories() == null) {
                    if (productRequest.attributes() == null) {
                        products = productRepository.searchByNameAndCurrencyCode(productRequest.name(), productRequest.currencyCode());
                    } else {
                        products = productRepository.searchByNameCurrencyAndColorAttribute(productRequest.name(), productRequest.currencyCode(), productRequest.attributes());
                    }
                } else {
                    if (productRequest.attributes() == null) {
                        products = productRepository.searchByNameCurrencyCodeAndCategories(productRequest.name(), productRequest.currencyCode(), productRequest.categories());
                    } else {
                        products = productRepository.searchByAllFilters(productRequest.name(), productRequest.currencyCode(), productRequest.categories(), productRequest.attributes());
                    }
                }
            }
        }
        return new ApiResponse(200, "Success", products);
    }
}
