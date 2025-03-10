package com.novamart.search_service.controller;

import com.novamart.search_service.dto.ApiResponse;
import com.novamart.search_service.dto.ProductRequest;
import com.novamart.search_service.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/search")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse searchProducts(@RequestBody ProductRequest productRequest) {
        return productService.searchProducts(productRequest);
    }
}
