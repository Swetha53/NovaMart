package com.novamart.order_service.controller;

import com.novamart.order_service.dto.OrderRequest;
import com.novamart.order_service.model.StatusHistory;
import com.novamart.order_service.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        orderService.placeOrder(orderRequest);
        return "Order Placed Successfully";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<?> getOrders(@RequestParam(required = false) String userId,
                                  @RequestParam(required = false) String merchantId) {
        if (userId != null) {
            return orderService.getUserOrders(userId);
        } else if (merchantId != null) {
            return orderService.getMerchantOrders(merchantId);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Either userId or merchantId should be provided");
        }
    }

    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public List<StatusHistory> getOrderStatusHistory(@RequestParam String orderId) {
        return orderService.getOrderStatusHistory(orderId);
    }

    @DeleteMapping("/clear")
    public void clearOrders() {
        orderService.clearOrders();
    }
}
