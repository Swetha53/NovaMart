package com.novamart.inventory_service.controller;

import com.novamart.inventory_service.dto.InventoryRequest;
import com.novamart.inventory_service.model.Inventory;
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
    public String createProductInventory(@RequestBody InventoryRequest inventoryRequest) {
        inventoryService.createProductInventory(inventoryRequest);
        return "Product " + inventoryRequest.productId() + " Created Successfully";
    }

    @PutMapping("/restock")
    @ResponseStatus(HttpStatus.OK)
    public String restockInventory(@RequestParam String productId, @RequestParam long quantity) {
        inventoryService.restockInventory(productId, quantity);
        return "Product " + productId + " Restocked Successfully";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Inventory getInventoryByProductId(@RequestParam String productId) {
        return inventoryService.getInventoryByProductId(productId);
    }

    @PutMapping("/reserve")
    @ResponseStatus(HttpStatus.OK)
    public String reserveInventory(@RequestParam String productId, @RequestParam long quantity) {
        inventoryService.reserveInventory(productId, quantity);
        return "Product " + productId + " Reserved Successfully";
    }

    @PutMapping("/sell")
    @ResponseStatus(HttpStatus.OK)
    public String sellInventory(@RequestParam String productId, @RequestParam long quantity) {
        inventoryService.sellInventory(productId, quantity);
        return "Product " + productId + " Sold Successfully";
    }

    @PutMapping("/release")
    @ResponseStatus(HttpStatus.OK)
    public String releaseInventory(@RequestParam String productId, @RequestParam long quantity) {
        inventoryService.releaseInventory(productId, quantity);
        return "Product " + productId + " Released Successfully";
    }

    @DeleteMapping("/delete-all")
    @ResponseStatus(HttpStatus.OK)
    public String deleteAllInventory() {
        inventoryService.deleteAllInventory();
        return "All Inventory Deleted Successfully";
    }
}
