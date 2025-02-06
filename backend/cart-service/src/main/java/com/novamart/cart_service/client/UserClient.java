package com.novamart.cart_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", url = "http://localhost:8085")
public interface UserClient {
    @RequestMapping(method = RequestMethod.GET, value = "/api/users/authenticate")
    boolean authenticateUser(@RequestParam String userId, @RequestParam String checkField, @RequestParam String value);
}
