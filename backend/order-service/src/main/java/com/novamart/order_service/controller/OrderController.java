package com.novamart.order_service.controller;

import com.novamart.order_service.dto.ApiResponse;
import com.novamart.order_service.dto.OrderRequest;
import com.novamart.order_service.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse placeOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.placeOrder(orderRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getOrders(@RequestParam(required = false) String userId,
                                  @RequestParam(required = false) String merchantId) {
        if (userId != null) {
            return orderService.getUserOrders(userId);
        } else if (merchantId != null) {
            return orderService.getMerchantOrders(merchantId);
        } else {
            return new ApiResponse(401, "Bad Request", null);
        }
    }

    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getOrderStatusHistory(@RequestParam String orderId) {
        return orderService.getOrderStatusHistory(orderId);
    }

    @PutMapping("/cancel")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse cancelOrder(@RequestParam String orderId, @RequestParam String userId, @RequestParam String userEmail) {
        return orderService.cancelOrder(orderId, userId, userEmail);
    }

    @PutMapping("/deliver")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse deliverOrder(@RequestParam String orderId, @RequestParam String userEmail) {
        return orderService.deliverOrder(orderId, userEmail);
    }

    @DeleteMapping("/clear")
    public ApiResponse clearOrders(@RequestParam String userId) {
        return orderService.clearOrders(userId);
    }
}
