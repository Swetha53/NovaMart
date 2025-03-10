package com.novamart.user_service.service;

import com.novamart.user_service.dto.ApiResponse;
import com.novamart.user_service.dto.LoginRequest;
import com.novamart.user_service.dto.PasswordResetRequest;
import com.novamart.user_service.dto.UserRequest;
import com.novamart.user_service.model.User;
import com.novamart.user_service.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public ApiResponse registerUser(UserRequest userRequest) {
        Map<String, List<String>> roles = new HashMap<>();

        roles.put("MERCHANT", Arrays.asList("CREATE", "UPDATE", "VIEW"));
        roles.put("CUSTOMER", Collections.singletonList("VIEW"));
        roles.put("ADMIN", Collections.singletonList("VIEW"));
        User user = User.builder()
                .userId(UUID.randomUUID().toString())
                .email(userRequest.email())
                .password(userRequest.password())
                .firstName(userRequest.firstName())
                .lastName(userRequest.lastName())
                .age(userRequest.age())
                .gender(userRequest.gender())
                .phone(userRequest.phone())
                .address(userRequest.address())
                .avatar(userRequest.avatar())
                .accountType(userRequest.accountType())
                .roles(roles.get(userRequest.accountType()))
                .preferences(userRequest.preferences())
                .createdAt(System.currentTimeMillis())
                .updatedAt(System.currentTimeMillis())
                .build();
        userRepository.save(user);

        publishKafkaEvent(userRequest.email(), "profile-completed");
        return new ApiResponse(200, "User registered successfully", null);
    }

    public ApiResponse loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.email());
        if (user == null) {
            return new ApiResponse(404, "User not found", null);
        }
        if (!user.getPassword().equals(loginRequest.password())) {
            return new ApiResponse(401, "Invalid password", null);
        }
        List<User> users = Collections.singletonList(user);
        return new ApiResponse(200, "User logged in successfully", users);
    }
//    logout
    public ApiResponse getUser(String userId) {
        List<User> users = Collections.singletonList(userRepository.findByUserId(userId));
        return new ApiResponse(200, "User details fetched successfully", users);
    }

    public ApiResponse updateUser(UserRequest userRequest) {
        User user = userRepository.findByEmail(userRequest.email());
        if (user == null) {
            return new ApiResponse(404, "User not found", null);
        }
        if (!user.getPassword().equals(userRequest.password()) || !user.getEmail().equals(userRequest.email())) {
            return new ApiResponse(401, "Invalid credentials", null);
        }
        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.lastName());
        user.setAge(userRequest.age());
        user.setGender(userRequest.gender());
        user.setPhone(userRequest.phone());
        user.setAddress(userRequest.address());
        user.setAvatar(userRequest.avatar());
        user.setPreferences(userRequest.preferences());
        user.setUpdatedAt(System.currentTimeMillis());
        userRepository.save(user);
        List<User> users = Collections.singletonList(user);

        publishKafkaEvent(userRequest.email(), "profile-updated");
        return new ApiResponse(200, "User updated successfully", users);
    }

    public ApiResponse resetPassword(PasswordResetRequest passwordResetRequest) {
        User user = userRepository.findByEmail(passwordResetRequest.email());
        if (user == null) {
            return new ApiResponse(404, "User not found", null);
        }
        if (!user.getPassword().equals(passwordResetRequest.token())) {
            return new ApiResponse(401, "Invalid token", null);
        }
        user.setPassword(passwordResetRequest.password());
        user.setUpdatedAt(System.currentTimeMillis());
        userRepository.save(user);

        return new ApiResponse(200, "Password reset successfully", null);
    }

    public ApiResponse deleteUser(String userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return new ApiResponse(404, "User not found", null);
        }

        publishKafkaEvent(user.getEmail(), "profile-deleted");
        userRepository.delete(user);
        return new ApiResponse(200, "User deleted successfully", null);
    }

    public ApiResponse authenticateUser(String userId, String checkField, String value) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return new ApiResponse(404, "User not found", null);
        }
        boolean isValidUser;
        if (checkField.equals("role")) {
            isValidUser = user.getRoles().contains(value);
        } else if (checkField.equals("accountType")) {
            isValidUser = user.getAccountType().equals(value);
        } else {
            return new ApiResponse(400, "Invalid check field", null);
        }

        if (!isValidUser) {
            return new ApiResponse(401, "User not authorized", null);
        } else {
            return new ApiResponse(200, "User authorized", null);
        }
    }

    public ApiResponse deleteAllUsers() {
        userRepository.deleteAll();
        return new ApiResponse(200, "All users deleted successfully", null);
    }

    public ApiResponse getAllUsers() {
        return new ApiResponse(200, "All users fetched successfully", userRepository.findAll());
    }

    public void publishKafkaEvent(String userEmail, String topic) {
        try {
            kafkaTemplate.send(topic, userEmail);
        } catch (Exception e) {
            log.error("Error publishing order placed: {}", e.getMessage());
        }
    }
}
