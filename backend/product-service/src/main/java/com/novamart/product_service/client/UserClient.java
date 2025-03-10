package com.novamart.product_service.client;

import com.novamart.product_service.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", url = "http://localhost:8095")
public interface UserClient {
    @RequestMapping(method = RequestMethod.GET, value = "/api/users/authenticate")
    ApiResponse authenticateUser(@RequestParam String userId, @RequestParam String checkField, @RequestParam String value);
}
