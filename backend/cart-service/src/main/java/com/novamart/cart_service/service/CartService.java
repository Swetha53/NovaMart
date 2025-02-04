package com.novamart.cart_service.service;

import com.novamart.cart_service.dto.CartRequest;
import com.novamart.cart_service.dto.CartResponse;
import com.novamart.cart_service.model.Cart;
import com.novamart.cart_service.model.CartItem;
import com.novamart.cart_service.repository.CartItemRepository;
import com.novamart.cart_service.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public void updateAndSaveCart(CartRequest cartRequest) {
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

        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(cartRequest.userId() + cartRequest.productId());
        cartItem.setUserId(cart.getUserId());
        cartItem.setProductId(cartRequest.productId());
        cartItem.setQuantity(cartRequest.quantity());
        cartItem.setUnitPrice(cartRequest.unitPrice());
        cartItem.setTotalPrice(cartRequest.unitPrice().multiply(new BigDecimal(cartRequest.quantity())));
        cartItem.setAddedAt(System.currentTimeMillis());

        cart.setTotalAmount(calculateTotalAmount(cartItem.getTotalPrice(), oldCartItems));

        cartRepository.save(cart);
        cartItemRepository.save(cartItem);
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

    public CartResponse getCart(String userId) {
        Cart cart = cartRepository.findByUserId(userId);
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);

        if (cart == null) {
            cart = new Cart();
        }
        return new CartResponse(
                cart.getUserId(),
                cart.getTotalAmount(),
                cart.getCurrencyCode(),
                cart.getCreatedAt(),
                cart.getUpdatedAt(),
                cartItems
        );
    }

    @Transactional
    public void clearCart(String userId) {
        cartItemRepository.deleteByUserId(userId);
        cartRepository.deleteByUserId(userId);
    }

    @Transactional
    public void deleteCartItem(String userId, String productId) {
        cartItemRepository.deleteByUserIdAndProductId(userId, productId);
        Cart cart = cartRepository.findByUserId(userId);
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        cart.setTotalAmount(calculateTotalAmount(new BigDecimal(0), cartItems));
        cartRepository.save(cart);
    }

    @Transactional
    public void deleteAllCarts() {
        cartItemRepository.deleteAll();
        cartRepository.deleteAll();
    }
}
