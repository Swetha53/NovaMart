package com.novamart.user_service.controller;

import com.novamart.user_service.dto.ApiResponse;
import com.novamart.user_service.dto.LoginRequest;
import com.novamart.user_service.dto.PasswordResetRequest;
import com.novamart.user_service.dto.UserRequest;
import com.novamart.user_service.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse registerUser(@RequestBody UserRequest userRequest) {
        return userService.registerUser(userRequest);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse loginUser(@RequestBody LoginRequest loginRequest) {
        return userService.loginUser(loginRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getUser(@RequestParam String userId) {
        return userService.getUser(userId);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse updateUser(@RequestBody UserRequest userRequest) {
        return userService.updateUser(userRequest);
    }

    @PutMapping("/reset-password")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) {
        return userService.resetPassword(passwordResetRequest);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse deleteUser(@RequestParam String userId) {
        return userService.deleteUser(userId);
    }

    @GetMapping("/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse authenticateUser(@RequestParam String userId, @RequestParam String checkField,
                                    @RequestParam String value) {
        return userService.authenticateUser(userId, checkField, value);
    }

    @DeleteMapping("/delete-all")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse deleteAllUsers() {
        return userService.deleteAllUsers();
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAllUsers() {
        return userService.getAllUsers();
    }
}
