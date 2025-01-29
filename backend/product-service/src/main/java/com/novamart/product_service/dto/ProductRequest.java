package com.novamart.product_service.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductRequest(String productId, String merchantId, String name, String description, BigDecimal price,
                             String currencyCode, List<String> categories, Object attributes) {
}
