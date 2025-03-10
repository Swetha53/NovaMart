package com.novamart.cart_service.dto;

import java.util.List;

public record ApiResponse(int status, String message, List<?> body) {
}
