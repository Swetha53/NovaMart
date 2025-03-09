package com.novamart.order_service.service;

import com.novamart.order_service.client.InventoryClient;
import com.novamart.order_service.client.UserClient;
import com.novamart.order_service.dto.OrderRequest;
import com.novamart.order_service.dto.ReservationRequest;
import com.novamart.order_service.dto.UserOrderResponse;
import com.novamart.order_service.model.Order;
import com.novamart.order_service.model.OrderItem;
import com.novamart.order_service.model.StatusHistory;
import com.novamart.order_service.repository.OrderItemRepository;
import com.novamart.order_service.repository.OrderRepository;
import com.novamart.order_service.repository.StatusHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final StatusHistoryRepository statusHistoryRepository;
    private final InventoryClient inventoryClient;
    private final UserClient userClient;

    public void placeOrder(OrderRequest orderRequest) {
        if (userClient.authenticateUser(orderRequest.userId(), "accountType","CUSTOMER")) {
//            Save order details
            Order order = new Order();

            order.setOrderId(UUID.randomUUID().toString());
            order.setUserId(orderRequest.userId());
            order.setCustomerName(orderRequest.customerName());
            order.setStatus("CONFIRMED");
            order.setTotalAmount(orderRequest.totalAmount());
            order.setCurrencyCode(orderRequest.currencyCode());
            order.setCreatedAt(System.currentTimeMillis());
            order.setUpdatedAt(System.currentTimeMillis());

    //        Save Order Item Details
            List<OrderItem> orderItemList = new ArrayList<>();
            for (OrderItem orderRequestItem : orderRequest.orderItemList()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderItemId(UUID.randomUUID().toString());
                orderItem.setOrderId(order.getOrderId());
                orderItem.setProductId(orderRequestItem.getProductId());
                orderItem.setMerchantId(orderRequestItem.getMerchantId());
                orderItem.setQuantity(orderRequestItem.getQuantity());
                orderItem.setUnitPrice(orderRequestItem.getUnitPrice());
                orderItem.setTotalPrice(orderRequestItem.getTotalPrice());
                orderItem.setCreatedAt(System.currentTimeMillis());

                orderItemList.add(orderItem);

                ReservationRequest reservationRequest = new ReservationRequest(
                        order.getOrderId(),
                        order.getUserId(),
                        orderItem.getProductId(),
                        orderItem.getQuantity()
                );
                log.info(inventoryClient.reserveInventory(reservationRequest));
            }

    //        Save Order Status History Details
            StatusHistory statusHistory = new StatusHistory();
            statusHistory.setStatusHistoryId(UUID.randomUUID().toString());
            statusHistory.setOrderId(order.getOrderId());
            statusHistory.setOldStatus(null);
            statusHistory.setNewStatus(order.getStatus());
            statusHistory.setChangedAt(System.currentTimeMillis());

            orderRepository.save(order);
            orderItemRepository.saveAll(orderItemList);
            statusHistoryRepository.save(statusHistory);
        } else {
            throw new RuntimeException("User not authenticated");
        }
    }

    public List<UserOrderResponse> getUserOrders(String userId) {
        if (!userClient.authenticateUser(userId, "accountType", "CUSTOMER")) {
            throw new RuntimeException("User not authenticated");
        }
        List<Order> orders = orderRepository.findByUserId(userId);
        List<UserOrderResponse> userOrderResponse = new ArrayList<>();

        for (Order order : orders) {
            List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getOrderId());
            userOrderResponse.add(new UserOrderResponse(order.getOrderId(), order.getUserId(),
                    order.getStatus(), order.getCustomerName(), order.getTotalAmount(), order.getCurrencyCode(),
                    order.getCreatedAt(), orderItems));
        }

        return userOrderResponse;
    }

    public List<OrderItem> getMerchantOrders(String merchantId) {
        if (!userClient.authenticateUser(merchantId, "accountType", "MERCHANT")) {
            throw new RuntimeException("User not authenticated");
        }
        return orderItemRepository.findByMerchantId(merchantId);
    }

    public List<StatusHistory> getOrderStatusHistory(String orderId) {
        return statusHistoryRepository.findByOrderId(orderId);
    }

    public String deliverOrder(String orderId) {
        Order order = orderRepository.findByOrderId(orderId);
        if (order == null) {
            return "Order not found";
        }

        if (!order.getStatus().equals("CONFIRMED")) {
            return "Order cannot be delivered";
        }

        order.setStatus("DELIVERED");
        order.setUpdatedAt(System.currentTimeMillis());

        List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getOrderId());
        for (OrderItem orderItem : orderItems) {
            log.info(
                    inventoryClient.sellInventory(
                            order.getOrderId(), orderItem.getProductId(), orderItem.getQuantity()
                    )
            );
        }

        StatusHistory statusHistory = new StatusHistory();
        statusHistory.setStatusHistoryId(UUID.randomUUID().toString());
        statusHistory.setOrderId(order.getOrderId());
        statusHistory.setOldStatus("CONFIRMED");
        statusHistory.setNewStatus(order.getStatus());
        statusHistory.setChangedAt(System.currentTimeMillis());

        orderRepository.save(order);
        statusHistoryRepository.save(statusHistory);

        return "Order Delivered Successfully";
    }

    public String cancelOrder(String orderId, String userId) {
        if (!userClient.authenticateUser(userId, "accountType", "CUSTOMER")) {
            return "User not authenticated";
        }
        Order order = orderRepository.findByOrderId(orderId);
        if (order == null) {
            return "Order not found";
        }

        if (!order.getStatus().equals("CONFIRMED")) {
            return "Order cannot be cancelled";
        }

        order.setStatus("CANCELLED");
        order.setUpdatedAt(System.currentTimeMillis());

        List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getOrderId());
        for (OrderItem orderItem : orderItems) {
            log.info(
                    inventoryClient.releaseInventory(
                            order.getOrderId(), orderItem.getProductId(), orderItem.getQuantity()
                    )
            );
        }

        StatusHistory statusHistory = new StatusHistory();
        statusHistory.setStatusHistoryId(UUID.randomUUID().toString());
        statusHistory.setOrderId(order.getOrderId());
        statusHistory.setOldStatus("CONFIRMED");
        statusHistory.setNewStatus(order.getStatus());
        statusHistory.setChangedAt(System.currentTimeMillis());

        orderRepository.save(order);
        statusHistoryRepository.save(statusHistory);

        return "Order Cancelled Successfully";
    }

    @Transactional
    public void clearOrders(String userId) {
        if (!userClient.authenticateUser(userId, "accountType", "ADMIN")) {
            throw new RuntimeException("User not authenticated");
        }
        orderItemRepository.deleteAll();
        statusHistoryRepository.deleteAll();
        orderRepository.deleteAll();
    }
}
