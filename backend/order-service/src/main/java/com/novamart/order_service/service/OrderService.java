package com.novamart.order_service.service;

import com.novamart.order_service.client.InventoryClient;
import com.novamart.order_service.client.UserClient;
import com.novamart.order_service.dto.ApiResponse;
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
import org.springframework.kafka.core.KafkaTemplate;
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
    private final KafkaTemplate<String, String> kafkaTemplate;

    public ApiResponse placeOrder(OrderRequest orderRequest) {
        ApiResponse user = userClient.authenticateUser(orderRequest.userId(), "accountType","CUSTOMER");
        if (user != null && user.status() == 200) {
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
                inventoryClient.reserveInventory(reservationRequest);
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

            publishKafkaEvent(orderRequest.userEmail(), "order-placed");

            return new ApiResponse(200, "Order Placed Successfully", null);
        } else {
            return new ApiResponse(401, "User not authenticated", null);
        }
    }

    public ApiResponse getUserOrders(String userId) {
        ApiResponse user = userClient.authenticateUser(userId, "accountType", "CUSTOMER");
        if (user == null || user.status() != 200) {
            return new ApiResponse(401, "User not authenticated", null);
        }
        List<Order> orders = orderRepository.findByUserId(userId);
        List<UserOrderResponse> userOrderResponse = new ArrayList<>();

        for (Order order : orders) {
            List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getOrderId());
            userOrderResponse.add(new UserOrderResponse(order.getOrderId(), order.getUserId(),
                    order.getStatus(), order.getCustomerName(), order.getTotalAmount(), order.getCurrencyCode(),
                    order.getCreatedAt(), orderItems));
        }

        return new ApiResponse(200, "Success", userOrderResponse);
    }

    public ApiResponse getMerchantOrders(String merchantId) {
        ApiResponse user = userClient.authenticateUser(merchantId, "accountType", "MERCHANT");
        if (user == null || user.status() != 200) {
            return new ApiResponse(401, "User not authenticated", null);
        }
        return new ApiResponse(200, "Success", orderItemRepository.findByMerchantId(merchantId));
    }

    public ApiResponse getOrderStatusHistory(String orderId) {
        return new ApiResponse(200, "Success", statusHistoryRepository.findByOrderId(orderId));
    }

    public ApiResponse deliverOrder(String orderId, String userEmail) {
        Order order = orderRepository.findByOrderId(orderId);
        if (order == null) {
            return new ApiResponse(404, "Order not found", null);
        }

        if (!order.getStatus().equals("CONFIRMED")) {
            return new ApiResponse(400, "Order cannot be delivered", null);
        }

        order.setStatus("DELIVERED");
        order.setUpdatedAt(System.currentTimeMillis());

        List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getOrderId());
        for (OrderItem orderItem : orderItems) {
            inventoryClient.sellInventory(order.getOrderId(), orderItem.getProductId(), orderItem.getQuantity());
        }

        StatusHistory statusHistory = new StatusHistory();
        statusHistory.setStatusHistoryId(UUID.randomUUID().toString());
        statusHistory.setOrderId(order.getOrderId());
        statusHistory.setOldStatus("CONFIRMED");
        statusHistory.setNewStatus(order.getStatus());
        statusHistory.setChangedAt(System.currentTimeMillis());

        orderRepository.save(order);
        statusHistoryRepository.save(statusHistory);

        publishKafkaEvent(userEmail, "order-delivered");

        return new ApiResponse(200, "Order Delivered Successfully", null);
    }

    public ApiResponse cancelOrder(String orderId, String userId, String userEmail) {
        ApiResponse user = userClient.authenticateUser(userId, "accountType", "CUSTOMER");
        if (user == null || user.status() != 200) {
            return new ApiResponse(401, "User not authenticated", null);
        }
        Order order = orderRepository.findByOrderId(orderId);
        if (order == null) {
            return new ApiResponse(404, "Order not found", null);
        }

        if (!order.getStatus().equals("CONFIRMED")) {
           return new ApiResponse(400, "Order cannot be cancelled", null);
        }

        order.setStatus("CANCELLED");
        order.setUpdatedAt(System.currentTimeMillis());

        List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getOrderId());
        for (OrderItem orderItem : orderItems) {
            inventoryClient.releaseInventory(order.getOrderId(), orderItem.getProductId(), orderItem.getQuantity());
        }

        StatusHistory statusHistory = new StatusHistory();
        statusHistory.setStatusHistoryId(UUID.randomUUID().toString());
        statusHistory.setOrderId(order.getOrderId());
        statusHistory.setOldStatus("CONFIRMED");
        statusHistory.setNewStatus(order.getStatus());
        statusHistory.setChangedAt(System.currentTimeMillis());

        orderRepository.save(order);
        statusHistoryRepository.save(statusHistory);

        publishKafkaEvent(userEmail, "order-cancelled");

        return new ApiResponse(200, "Order Cancelled Successfully", null);
    }

    @Transactional
    public ApiResponse clearOrders(String userId) {
        ApiResponse user = userClient.authenticateUser(userId, "accountType", "ADMIN");
        if (user == null || user.status() != 200) {
            return new ApiResponse(401, "User not authenticated", null);
        }
        orderItemRepository.deleteAll();
        statusHistoryRepository.deleteAll();
        orderRepository.deleteAll();

        return new ApiResponse(200, "All Orders Cleared Successfully", null);
    }

    public void publishKafkaEvent(String userEmail, String topic) {
        try {
            kafkaTemplate.send(topic, userEmail);
        } catch (Exception e) {
            log.error("Error publishing order placed: {}", e.getMessage());
        }
    }
}
