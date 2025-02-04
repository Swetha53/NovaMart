package com.novamart.inventory_service.service;

import com.novamart.inventory_service.dto.InventoryRequest;
import com.novamart.inventory_service.model.Inventory;
import com.novamart.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public void createProductInventory(InventoryRequest inventoryRequest) {
        Inventory inventory = new Inventory();
        inventory.setInventoryId(UUID.randomUUID().toString());
        inventory.setProductId(inventoryRequest.productId());
        inventory.setProductName(inventoryRequest.productName());
        inventory.setQuantityAvailable(inventoryRequest.quantityAvailable());
        inventory.setQuantitySold(0);
        inventory.setQuantityReserved(0);
        inventory.setLastUpdated(System.currentTimeMillis());

        inventoryRepository.save(inventory);
    }

    public void restockInventory(String productId, long quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId);
        inventory.setQuantityAvailable(inventory.getQuantityAvailable() + quantity);
        inventory.setLastUpdated(System.currentTimeMillis());
        inventoryRepository.save(inventory);
    }

    public Inventory getInventoryByProductId(String productId) {
        return inventoryRepository.findByProductId(productId);
    }

    public void reserveInventory(String productId, long quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId);
        inventory.setQuantityAvailable(inventory.getQuantityAvailable() - quantity);
        inventory.setQuantityReserved(inventory.getQuantityReserved() + quantity);
        inventory.setLastUpdated(System.currentTimeMillis());
        inventoryRepository.save(inventory);
    }

    public void sellInventory(String productId, long quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId);
        long reservedStock = inventory.getQuantityReserved();
        if (reservedStock < quantity) {
            throw new RuntimeException("Insufficient stock");
        }
        inventory.setQuantityReserved(reservedStock - quantity);
        inventory.setQuantitySold(inventory.getQuantitySold() + quantity);
        inventory.setLastUpdated(System.currentTimeMillis());
        inventoryRepository.save(inventory);
    }

    public void releaseInventory(String productId, long quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId);
        long reservedStock = inventory.getQuantityReserved();
        if (reservedStock < quantity) {
            throw new RuntimeException("Insufficient stock");
        }
        inventory.setQuantityReserved(reservedStock - quantity);
        inventory.setQuantityAvailable(inventory.getQuantityAvailable() + quantity);
        inventory.setLastUpdated(System.currentTimeMillis());
        inventoryRepository.save(inventory);
    }
//    TODO connect to reservation service

    public void deleteAllInventory() {
        inventoryRepository.deleteAll();
    }
}
