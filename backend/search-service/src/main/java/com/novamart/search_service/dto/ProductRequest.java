package com.novamart.search_service.dto;

import java.util.List;

public record ProductRequest(String name, String currencyCode, List<String> categories, Object attributes) {
}
