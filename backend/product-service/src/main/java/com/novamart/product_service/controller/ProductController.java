package com.novamart.product_service.controller;

import com.novamart.product_service.dto.ProductRequest;
import com.novamart.product_service.dto.ProductResponse;
import com.novamart.product_service.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
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

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getProducts(@RequestParam(required = false) String productId,
                                      @RequestParam(required = false) String merchantId) {
        if (productId == null) {
            return productService.getMerchantProducts(merchantId);
        } else if (merchantId == null) {
            return productService.getProduct(productId);
        } else {
            throw new RuntimeException("Invalid request");
        }
    }

    @DeleteMapping("/clear")
    public String clearAllProducts(@RequestParam String userId) {
        productService.clearAllProducts(userId);
        return "All products deleted successfully";
    }
}
