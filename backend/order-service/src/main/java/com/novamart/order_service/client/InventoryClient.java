package com.novamart.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-service", url = "http://localhost:8084")
public interface InventoryClient {
    @RequestMapping(method = RequestMethod.PUT, value = "/api/inventory/reserve")
    String reserveInventory(@RequestParam String productId, @RequestParam long quantity);

    @RequestMapping(method = RequestMethod.PUT, value = "/api/inventory/sell")
    String sellInventory(@RequestParam String productId, @RequestParam long quantity);

    @RequestMapping(method = RequestMethod.PUT, value = "/api/inventory/release")
    String releaseInventory(@RequestParam String productId, @RequestParam long quantity);
}
