package com.novamart.user_service.service;

import com.novamart.user_service.dto.LoginRequest;
import com.novamart.user_service.dto.PasswordResetRequest;
import com.novamart.user_service.dto.UserRequest;
import com.novamart.user_service.model.User;
import com.novamart.user_service.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public void registerUser(UserRequest userRequest) {
        Map<String, List<String>> roles = new HashMap<>();

        roles.put("MERCHANT", Arrays.asList("CREATE", "UPDATE", "VIEW"));
        roles.put("CUSTOMER", Collections.singletonList("VIEW"));
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
        log.info("User registered successfully");
    }

    public User loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.email());
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        if (!user.getPassword().equals(loginRequest.password())) {
            throw new RuntimeException("Invalid password");
        }
        log.info("User logged in successfully");
        return user;
    }
//    logout
    public User getUser(String userId) {
        return userRepository.findByUserId(userId);
    }

    public User updateUser(UserRequest userRequest) {
        User user = userRepository.findByEmail(userRequest.email());
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        if (!user.getPassword().equals(userRequest.password()) || !user.getEmail().equals(userRequest.email())) {
            throw new RuntimeException("Invalid password");
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
        log.info("User details updated successfully");
        return user;
    }

    public void resetPassword(PasswordResetRequest passwordResetRequest) {
        User user = userRepository.findByEmail(passwordResetRequest.email());
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        if (!user.getPassword().equals(passwordResetRequest.token())) {
            throw new RuntimeException("Invalid token");
        }
        user.setPassword(passwordResetRequest.password());
        user.setUpdatedAt(System.currentTimeMillis());
        userRepository.save(user);
        log.info("Password updated successfully");
    }

    public void deleteUser(String userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        userRepository.delete(user);
        log.info("User deleted successfully");
    }

    public boolean authenticateUser(String userId, String checkField, String value) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        if (checkField.equals("role")) {
            return user.getRoles().contains(value);
        } else if (checkField.equals("accountType")) {
            return user.getAccountType().equals(value);
        } else {
            throw new RuntimeException("Invalid checkField");
        }
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
