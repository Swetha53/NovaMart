package com.novamart.user_service.controller;

import com.novamart.user_service.dto.LoginRequest;
import com.novamart.user_service.dto.PasswordResetRequest;
import com.novamart.user_service.dto.UserRequest;
import com.novamart.user_service.model.User;
import com.novamart.user_service.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody UserRequest userRequest) {
        userService.registerUser(userRequest);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public User loginUser(@RequestBody LoginRequest loginRequest) {
        return userService.loginUser(loginRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@RequestParam String userId) {
        return userService.getUser(userId);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@RequestBody UserRequest userRequest) {
        return userService.updateUser(userRequest);
    }

    @PutMapping("/reset-password")
    @ResponseStatus(HttpStatus.OK)
    public void resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) {
        userService.resetPassword(passwordResetRequest);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@RequestParam String userId) {
        userService.deleteUser(userId);
    }
}
