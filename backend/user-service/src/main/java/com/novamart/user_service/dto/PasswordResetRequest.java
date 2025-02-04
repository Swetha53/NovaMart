package com.novamart.user_service.dto;

public record PasswordResetRequest(String email, String password, String token) {
}
