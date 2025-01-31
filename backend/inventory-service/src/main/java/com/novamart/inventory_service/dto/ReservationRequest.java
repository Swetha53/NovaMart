package com.novamart.inventory_service.dto;

public record ReservationRequest(String orderId, String userId, String inventoryId, String productId, long quantity) {
}
