package com.novamart.inventory_service.controller;

import com.novamart.inventory_service.dto.ApiResponse;
import com.novamart.inventory_service.dto.InventoryRequest;
import com.novamart.inventory_service.dto.ReserveInventoryRequest;
import com.novamart.inventory_service.service.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@AllArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse createProductInventory(@RequestBody InventoryRequest inventoryRequest) {
        return inventoryService.createProductInventory(inventoryRequest);
    }

    @PutMapping("/restock")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse restockInventory(@RequestParam String productId, @RequestParam long quantity) {
        return inventoryService.restockInventory(productId, quantity);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getInventoryByProductId(@RequestParam String productId) {
        return inventoryService.getInventoryByProductId(productId);
    }

    @PutMapping("/reserve")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse reserveInventory(@RequestBody ReserveInventoryRequest reserveInventoryRequest) {
        return inventoryService.reserveInventory(reserveInventoryRequest);
    }

    @PutMapping("/sell")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse sellInventory(@RequestParam String orderId, @RequestParam String productId,
                                @RequestParam long quantity) {
        return inventoryService.sellInventory(orderId, productId, quantity);
    }

    @PutMapping("/release")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse releaseInventory(@RequestParam String orderId, @RequestParam String productId,
                                   @RequestParam long quantity) {
        return inventoryService.releaseInventory(orderId, productId, quantity);
    }

    @DeleteMapping("/delete-all")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse deleteAllInventory() {
        return inventoryService.deleteAllInventory();
    }
}
