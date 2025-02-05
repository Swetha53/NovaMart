package com.novamart.inventory_service.dto;

public record ReserveInventoryRequest(String orderId, String userId, String productId, long quantity) {
}
