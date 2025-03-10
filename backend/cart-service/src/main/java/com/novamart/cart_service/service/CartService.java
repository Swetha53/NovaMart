package com.novamart.cart_service.service;

import com.novamart.cart_service.client.ProductClient;
import com.novamart.cart_service.client.UserClient;
import com.novamart.cart_service.dto.ApiResponse;
import com.novamart.cart_service.dto.CartRequest;
import com.novamart.cart_service.dto.CartResponse;
import com.novamart.cart_service.dto.ProductResponse;
import com.novamart.cart_service.model.Cart;
import com.novamart.cart_service.model.CartItem;
import com.novamart.cart_service.repository.CartItemRepository;
import com.novamart.cart_service.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductClient productClient;
    private final UserClient userClient;

    public ApiResponse updateAndSaveCart(CartRequest cartRequest) {
//        Check product availability
        ApiResponse user = userClient.authenticateUser(cartRequest.userId(), "accountType", "CUSTOMER");
        if (user == null || user.status() != 200) {
            return new ApiResponse(401, "User not authenticated", null);
        }
        Cart oldCart = cartRepository.findByUserId(cartRequest.userId());
        Cart cart = new Cart();
        if (oldCart != null) {
            cart = oldCart;
            cart.setUpdatedAt(System.currentTimeMillis());
        } else {
            cart.setUserId(cartRequest.userId());
            cart.setCreatedAt(System.currentTimeMillis());
            cart.setUpdatedAt(System.currentTimeMillis());
            cart.setCurrencyCode(cartRequest.currencyCode());
        }
        List<CartItem> oldCartItems = cartItemRepository.findByUserId(cart.getUserId());
        oldCartItems.replaceAll(this::checkProduct);

        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(cartRequest.userId() + cartRequest.productId());
        cartItem.setUserId(cart.getUserId());
        cartItem.setProductId(cartRequest.productId());
        cartItem.setQuantity(cartRequest.quantity());
        cartItem.setUnitPrice(cartRequest.unitPrice());
        cartItem.setTotalPrice(cartRequest.unitPrice().multiply(new BigDecimal(cartRequest.quantity())));
        cartItem.setAddedAt(System.currentTimeMillis());
        cartItem.setStatus("AVAILABLE");
        checkProduct(cartItem);

        cart.setTotalAmount(calculateTotalAmount(cartItem.getTotalPrice(), oldCartItems));

        cartRepository.save(cart);
        cartItemRepository.save(cartItem);

        return new ApiResponse(200, "Cart updated successfully", null);
    }

    private CartItem checkProduct(CartItem cartItem) {
        List<ProductResponse> productResponse = (List<ProductResponse>) productClient.getProduct(cartItem.getProductId()).body();
        if (productResponse == null || productResponse.isEmpty()) {
            cartItem.setQuantity(0);
            cartItem.setStatus("DELETED");
        } else if (cartItem.getQuantity() > productResponse.getFirst().quantityAvailable()) {
            cartItem.setQuantity(0);
            cartItem.setStatus("OUT_OF_STOCK");
        } else if (!cartItem.getUnitPrice().equals(productResponse.getFirst().price())) {
            cartItem.setUnitPrice(productResponse.getFirst().price());
            cartItem.setTotalPrice(cartItem.getUnitPrice().multiply(new BigDecimal(cartItem.getQuantity())));
        }
        return cartItem;
    }

    private BigDecimal calculateTotalAmount(BigDecimal newProductPrice, List<CartItem> oldCartItems) {
        BigDecimal totalAmount = new BigDecimal(0);
        for (CartItem cartItem : oldCartItems) {
            totalAmount = totalAmount.add(cartItem.getTotalPrice());
        }
        totalAmount = totalAmount.add(newProductPrice);
        BigDecimal taxAmount = calculateTax(totalAmount);
        totalAmount = totalAmount.add(taxAmount);
        return totalAmount;
    }

    private BigDecimal calculateTax(BigDecimal totalAmount) {
        return totalAmount.multiply(new BigDecimal("0.1"));
    }

    public ApiResponse getCart(String userId) {
        ApiResponse user = userClient.authenticateUser(userId, "accountType", "CUSTOMER");
        if (user == null || user.status() != 200) {
            return new ApiResponse(401, "User not authenticated", null);
        }
        Cart cart = cartRepository.findByUserId(userId);
        List<CartItem> cartItems = new ArrayList<>();

        if (cart == null) {
            cart = new Cart();
        } else {
            cartItems = cartItemRepository.findByUserId(userId);
            for (int i = 0; i < cartItems.size(); i++) {
                cartItems.set(i, checkProduct(cartItems.get(i)));
            }
        }
        List<CartResponse> cartResponse = Collections.singletonList(new CartResponse(
                cart.getUserId(),
                cart.getTotalAmount(),
                cart.getCurrencyCode(),
                cart.getCreatedAt(),
                cart.getUpdatedAt(),
                cartItems
        ));
        return new ApiResponse(200, "Cart retrieved successfully", cartResponse);
    }

    @Transactional
    public ApiResponse clearCart(String userId) {
        ApiResponse user = userClient.authenticateUser(userId, "accountType", "MERCHANT");
        if (user == null || user.status() != 200) {
            return new ApiResponse(401, "User not authenticated", null);
        }
        cartItemRepository.deleteByUserId(userId);
        cartRepository.deleteByUserId(userId);

        return new ApiResponse(200, "Cart cleared successfully", null);
    }

    @Transactional
    public ApiResponse deleteCartItem(String userId, String productId) {
        ApiResponse user = userClient.authenticateUser(userId, "accountType", "CUSTOMER");
        if (user == null || user.status() != 200) {
            return new ApiResponse(401, "User not authenticated", null);
        }
        cartItemRepository.deleteByUserIdAndProductId(userId, productId);
        Cart cart = cartRepository.findByUserId(userId);
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        cart.setTotalAmount(calculateTotalAmount(new BigDecimal(0), cartItems));
        cartRepository.save(cart);

        return new ApiResponse(200, "Cart Item " + productId + " Deleted Successfully", null);
    }

    @Transactional
    public ApiResponse deleteAllCarts(String userId) {
        ApiResponse user = userClient.authenticateUser(userId, "accountType", "ADMIN");
        if (user == null || user.status() != 200) {
            return new ApiResponse(401, "User not authenticated", null);
        }
        cartItemRepository.deleteAll();
        cartRepository.deleteAll();

        return new ApiResponse(200, "All Carts Deleted Successfully", null);
    }
}
