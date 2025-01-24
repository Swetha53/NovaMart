package com.novamart.product_service.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductRequest(String product_id, String name, String description, BigDecimal price, String currency_code,
                             List<String> categories, Object attributes) {
}
