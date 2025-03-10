package com.novamart.inventory_service.service;

import com.novamart.inventory_service.dto.ApiResponse;
import com.novamart.inventory_service.dto.InventoryRequest;
import com.novamart.inventory_service.dto.ReservationRequest;
import com.novamart.inventory_service.dto.ReserveInventoryRequest;
import com.novamart.inventory_service.model.Inventory;
import com.novamart.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final ReservationService reservationService;

    public ApiResponse createProductInventory(InventoryRequest inventoryRequest) {
        Inventory inventory = new Inventory();
        inventory.setInventoryId(UUID.randomUUID().toString());
        inventory.setProductId(inventoryRequest.productId());
        inventory.setProductName(inventoryRequest.productName());
        inventory.setQuantityAvailable(inventoryRequest.quantityAvailable());
        inventory.setQuantitySold(0);
        inventory.setQuantityReserved(0);
        inventory.setLastUpdated(System.currentTimeMillis());

        inventoryRepository.save(inventory);
        return new ApiResponse(200, "Product " + inventoryRequest.productId() + " Created Successfully", null);
    }

    public ApiResponse restockInventory(String productId, long quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId);
        inventory.setQuantityAvailable(inventory.getQuantityAvailable() + quantity);
        inventory.setLastUpdated(System.currentTimeMillis());
        inventoryRepository.save(inventory);

        return new ApiResponse(200, "Product " + productId + " Restocked Successfully", null);
    }

    public ApiResponse getInventoryByProductId(String productId) {
        List<Inventory> inventoryList = Collections.singletonList(inventoryRepository.findByProductId(productId));
        return new ApiResponse(200, "Product " + productId + " Retrieved Successfully", inventoryList);
    }

    public ApiResponse reserveInventory(ReserveInventoryRequest reserveInventoryRequest) {
        Inventory inventory = inventoryRepository.findByProductId(reserveInventoryRequest.productId());
        inventory.setQuantityAvailable(inventory.getQuantityAvailable() - reserveInventoryRequest.quantity());
        inventory.setQuantityReserved(inventory.getQuantityReserved() + reserveInventoryRequest.quantity());
        inventory.setLastUpdated(System.currentTimeMillis());
        inventoryRepository.save(inventory);

        ReservationRequest reservationRequest = new ReservationRequest(
                reserveInventoryRequest.orderId(),
                reserveInventoryRequest.userId(),
                inventory.getInventoryId(),
                reserveInventoryRequest.productId(),
                reserveInventoryRequest.quantity()
        );
        reservationService.createReservation(reservationRequest);

        return new ApiResponse(200, "Product " + reserveInventoryRequest.productId() + " Reserved Successfully", null);
    }

    public ApiResponse sellInventory(String orderId, String productId, long quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId);
        long reservedStock = inventory.getQuantityReserved();
        if (reservedStock < quantity) {
            throw new RuntimeException("Insufficient stock");
        }
        inventory.setQuantityReserved(reservedStock - quantity);
        inventory.setQuantitySold(inventory.getQuantitySold() + quantity);
        inventory.setLastUpdated(System.currentTimeMillis());
        inventoryRepository.save(inventory);

        reservationService.deleteReservation(orderId, productId);

        return new ApiResponse(200, "Product " + productId + " Sold Successfully", null);
    }

    public ApiResponse releaseInventory(String orderId, String productId, long quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId);
        long reservedStock = inventory.getQuantityReserved();
        if (reservedStock < quantity) {
            throw new RuntimeException("Insufficient stock");
        }
        inventory.setQuantityReserved(reservedStock - quantity);
        inventory.setQuantityAvailable(inventory.getQuantityAvailable() + quantity);
        inventory.setLastUpdated(System.currentTimeMillis());
        inventoryRepository.save(inventory);

        reservationService.deleteReservation(orderId, productId);

        return new ApiResponse(200, "Product " + productId + " Released Successfully", null);
    }

    public ApiResponse deleteAllInventory() {
        reservationService.deleteAllReservations();
        inventoryRepository.deleteAll();

        return new ApiResponse(200, "All Inventory Deleted Successfully", null);
    }
}
