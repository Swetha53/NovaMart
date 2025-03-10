package com.novamart.cart_service.controller;

import com.novamart.cart_service.dto.ApiResponse;
import com.novamart.cart_service.dto.CartRequest;
import com.novamart.cart_service.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@AllArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse updateAndSaveCart(@RequestBody CartRequest cartRequest) {
        return cartService.updateAndSaveCart(cartRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getCart(@RequestParam String userId) {
        return cartService.getCart(userId);
    }

    @DeleteMapping("/clear")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse clearCart(@RequestParam String userId) {
        return cartService.clearCart(userId);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse deleteCartItem(@RequestParam String userId, @RequestParam String productId) {
        return cartService.deleteCartItem(userId, productId);
    }

    @DeleteMapping("/delete-all")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse deleteAllCarts(@RequestParam String userId) {
        return cartService.deleteAllCarts(userId);
    }
}
