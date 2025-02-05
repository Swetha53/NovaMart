package com.novamart.product_service.controller;

import com.novamart.product_service.dto.ProductRequest;
import com.novamart.product_service.dto.ProductResponse;
import com.novamart.product_service.model.Product;
import com.novamart.product_service.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest) {
        productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getProduct(@RequestParam String productId) {
        return productService.getProduct(productId);
    }

    @GetMapping("/{merchantId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getMerchantProducts(@RequestParam String merchantId) {
        return productService.getMerchantProducts(merchantId);
    }

    @DeleteMapping("/clear")
    public void clearAllProducts(@RequestParam String userId) {
        productService.clearAllProducts(userId);
    }
}
