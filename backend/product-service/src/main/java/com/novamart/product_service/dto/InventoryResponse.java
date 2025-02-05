package com.novamart.product_service.dto;

public record InventoryResponse(String inventoryId, String productId, String productName, long quantityAvailable,
                                 long quantitySold, long quantityReserved, long lastUpdated) {
}
