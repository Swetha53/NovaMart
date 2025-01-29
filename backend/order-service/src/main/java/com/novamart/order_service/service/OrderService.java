package com.novamart.order_service.service;

import com.novamart.order_service.dto.OrderRequest;
import com.novamart.order_service.dto.UserOrderResponse;
import com.novamart.order_service.model.Order;
import com.novamart.order_service.model.OrderItem;
import com.novamart.order_service.model.StatusHistory;
import com.novamart.order_service.repository.OrderItemRepository;
import com.novamart.order_service.repository.OrderRepository;
import com.novamart.order_service.repository.StatusHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final StatusHistoryRepository statusHistoryRepository;

    public void placeOrder(OrderRequest orderRequest) {
//        Save order details
        Order order = new Order();

        order.setOrderId(UUID.randomUUID().toString());
        order.setUserId(orderRequest.userId());
        order.setPaymentId(orderRequest.paymentId());
        order.setStatus("CONFIRMED");
        order.setTotalAmount(orderRequest.totalAmount());
        order.setCurrencyCode(orderRequest.currencyCode());
        order.setCreatedAt(System.currentTimeMillis());
        order.setUpdatedAt(System.currentTimeMillis());

        orderRepository.save(order);

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
        }

        orderItemRepository.saveAll(orderItemList);

//        Save Order Status History Details
        StatusHistory statusHistory = new StatusHistory();
        statusHistory.setStatusHistoryId(UUID.randomUUID().toString());
        statusHistory.setOrderId(order.getOrderId());
        statusHistory.setOldStatus(null);
        statusHistory.setNewStatus(order.getStatus());
        statusHistory.setChangedAt(System.currentTimeMillis());

        statusHistoryRepository.save(statusHistory);
    }

    public List<UserOrderResponse> getUserOrders(String userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        List<UserOrderResponse> userOrderRespons = new ArrayList<>();

        for (Order order : orders) {
            List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getOrderId());
            userOrderRespons.add(new UserOrderResponse(order.getOrderId(), order.getUserId(), order.getPaymentId(),
                    order.getStatus(), order.getTotalAmount(), order.getCurrencyCode(), order.getCreatedAt(),
                    orderItems));
        }

        return userOrderRespons;
    }

    public List<OrderItem> getMerchantOrders(String merchantId) {
        return orderItemRepository.findByMerchantId(merchantId);
    }

    public List<StatusHistory> getOrderStatusHistory(String orderId) {
        return statusHistoryRepository.findByOrderId(orderId);
    }

    @Transactional
    public void clearOrders() {
        orderItemRepository.deleteAll();
        statusHistoryRepository.deleteAll();
        orderRepository.deleteAll();
    }
}
