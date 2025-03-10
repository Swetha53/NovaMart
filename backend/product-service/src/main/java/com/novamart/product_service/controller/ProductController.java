package com.novamart.product_service.controller;

import com.novamart.product_service.dto.ApiResponse;
import com.novamart.product_service.dto.ProductRequest;
import com.novamart.product_service.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse createProduct(@RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getProducts(@RequestParam(required = false) String productId,
                                      @RequestParam(required = false) String merchantId) {
        if (productId == null) {
            return productService.getMerchantProducts(merchantId);
        } else if (merchantId == null) {
            return productService.getProduct(productId);
        } else {
            return new ApiResponse(401, "Bad Request", null);
        }
    }

    @DeleteMapping("/clear")
    public ApiResponse clearAllProducts(@RequestParam String userId) {
        return productService.clearAllProducts(userId);
    }
}
