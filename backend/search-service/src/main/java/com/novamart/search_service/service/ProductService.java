package com.novamart.search_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    public List<Product> searchProducts(ProductRequest productRequest) {
        if (productRequest.name() == null) {
            if (productRequest.currencyCode() == null) {
                if (productRequest.categories() == null) {
                    if (productRequest.attributes() == null) {
                        throw new RuntimeException("Request must contain at least one search parameter");
                    } else {
                        return productRepository.searchByColorAttribute(productRequest.attributes());
                    }
                } else {
                    if (productRequest.attributes() == null) {
                        return productRepository.searchByCategories(productRequest.categories());
                    } else {
                        return productRepository.searchByCategoriesAndColorAttribute(productRequest.categories(), productRequest.attributes());
                    }
                }
            } else {
                if (productRequest.categories() == null) {
                    if (productRequest.attributes() == null) {
                        return productRepository.searchByCurrencyCode(productRequest.currencyCode());
                    } else {
                        return productRepository.searchByCurrencyCodeAndColorAttribute(productRequest.currencyCode(), productRequest.attributes());
                    }
                } else {
                    if (productRequest.attributes() == null) {
                        return productRepository.searchByCurrencyCodeAndCategories(productRequest.currencyCode(), productRequest.categories());
                    } else {
                        return productRepository.searchByCurrencyCodeCategoriesAndColorAttribute(productRequest.currencyCode(), productRequest.categories(), productRequest.attributes());
                    }
                }
            }
        } else {
            if (productRequest.currencyCode() == null) {
                if (productRequest.categories() == null) {
                    if (productRequest.attributes() == null) {
                        return productRepository.searchByName(productRequest.name());
                    } else {
                        return productRepository.searchByNameAndColorAttribute(productRequest.name(), productRequest.attributes());
                    }
                } else {
                    if (productRequest.attributes() == null) {
                        return productRepository.searchByNameAndCategories(productRequest.name(), productRequest.categories());
                    } else {
                        return productRepository.searchByNameCategoriesAndColorAttribute(productRequest.name(), productRequest.categories(), productRequest.attributes());
                    }
                }
            } else {
                if (productRequest.categories() == null) {
                    if (productRequest.attributes() == null) {
                        return productRepository.searchByNameAndCurrencyCode(productRequest.name(), productRequest.currencyCode());
                    } else {
                        return productRepository.searchByNameCurrencyAndColorAttribute(productRequest.name(), productRequest.currencyCode(), productRequest.attributes());
                    }
                } else {
                    if (productRequest.attributes() == null) {
                        return productRepository.searchByNameCurrencyCodeAndCategories(productRequest.name(), productRequest.currencyCode(), productRequest.categories());
                    } else {
                        return productRepository.searchByAllFilters(productRequest.name(), productRequest.currencyCode(), productRequest.categories(), productRequest.attributes());
                    }
                }
            }
        }
    }
}
