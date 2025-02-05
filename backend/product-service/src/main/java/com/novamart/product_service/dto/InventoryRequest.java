package com.novamart.product_service.dto;

public record InventoryRequest(String productId, String productName, long quantityAvailable) {
}
