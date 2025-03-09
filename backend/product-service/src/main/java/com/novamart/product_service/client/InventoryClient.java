package com.novamart.product_service.client;

import com.novamart.product_service.dto.InventoryRequest;
import com.novamart.product_service.dto.InventoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-service", url = "http://localhost:8094")
public interface InventoryClient {
    @RequestMapping(method = RequestMethod.POST, value = "/api/inventory/create")
    String createProductInventory(@RequestBody InventoryRequest inventoryRequest);

    @RequestMapping(method = RequestMethod.PUT, value = "/api/inventory/restock")
    String restockInventory(@RequestParam String productId, @RequestParam long quantity);

    @RequestMapping(method = RequestMethod.GET, value = "/api/inventory")
    InventoryResponse getInventoryByProductId(@RequestParam String productId);

    @RequestMapping(method = RequestMethod.DELETE, value = "/api/inventory/delete-all")
    String deleteAllInventory();
}
