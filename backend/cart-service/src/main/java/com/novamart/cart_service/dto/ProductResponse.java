package com.novamart.cart_service.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductResponse(String productId, String merchantId, String name, List<String> images, String description,
                              BigDecimal price, String currencyCode, List<String> categories, List<Reviews> reviews,
                              long createdAt, long updatedAt, String status, Object attributes, long quantityAvailable,
                              long quantitySold, long quantityReserved) {
}
