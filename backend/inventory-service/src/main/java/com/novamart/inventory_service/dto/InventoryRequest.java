package com.novamart.inventory_service.dto;

public record InventoryRequest(String productId, String productName, long quantityAvailable) {
}
