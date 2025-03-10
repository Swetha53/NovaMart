package com.novamart.search_service.dto;

import com.novamart.search_service.model.Product;

import java.util.List;

public record ApiResponse(int status, String message, List<Product> body) {
}
