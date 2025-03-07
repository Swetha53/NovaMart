package com.novamart.search_service.controller;

import com.novamart.search_service.dto.ProductRequest;
import com.novamart.search_service.model.Product;
import com.novamart.search_service.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/search")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> searchProducts(@RequestBody ProductRequest productRequest) {
        return productService.searchProducts(productRequest);
    }

//    @GetMapping("/{name}")
//    @ResponseStatus(HttpStatus.OK)
//    public List<Product> searchProductByName(@PathVariable String name) {
//        return productService.searchProductByName(name);
//    }
}
