package com.novamart.cart_service.client;

import com.novamart.cart_service.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "product-service", url = "http://localhost:8090")
public interface ProductClient {
    @RequestMapping(method = RequestMethod.GET, value = "/api/products")
    List<ProductResponse> getProduct(@RequestParam("productId") String productId);
}
