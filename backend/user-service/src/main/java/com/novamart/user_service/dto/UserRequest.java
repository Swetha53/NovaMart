package com.novamart.user_service.dto;

import java.util.List;

public record UserRequest(String firstName, String lastName, String email, String password, int age, String gender,
                          String phone, String address, String avatar, String accountType, List<String> preferences) {
}
