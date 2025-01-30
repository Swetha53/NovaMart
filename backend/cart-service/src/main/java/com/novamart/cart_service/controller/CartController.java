package com.novamart.cart_service.controller;

import com.novamart.cart_service.dto.CartRequest;
import com.novamart.cart_service.dto.CartResponse;
import com.novamart.cart_service.model.Cart;
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
    public String updateAndSaveCart(@RequestBody CartRequest cartRequest) {
        cartService.updateAndSaveCart(cartRequest);
        return "Cart Updated Successfully";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CartResponse getCart(@RequestParam String userId) {
        return cartService.getCart(userId);
    }

    @DeleteMapping("/clear")
    @ResponseStatus(HttpStatus.OK)
    public String clearCart(@RequestParam String userId) {
        cartService.clearCart(userId);
        return "Cart Cleared Successfully";
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public String deleteCartItem(@RequestParam String userId, @RequestParam String productId) {
        cartService.deleteCartItem(userId, productId);
        return "Cart Item " + productId + " Deleted Successfully";
    }
}
