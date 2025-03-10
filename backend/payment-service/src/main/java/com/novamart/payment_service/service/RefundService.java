package com.novamart.payment_service.service;

import com.novamart.payment_service.client.UserClient;
import com.novamart.payment_service.dto.ApiResponse;
import com.novamart.payment_service.dto.RefundRequest;
import com.novamart.payment_service.model.Refund;
import com.novamart.payment_service.respository.RefundRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefundService {
    private final RefundRepository refundRepository;
    private final UserClient userClient;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public ApiResponse initiateRefund(RefundRequest refundRequest) {
        ApiResponse user = userClient.authenticateUser(refundRequest.userId(), "accountType", "CUSTOMER");
        if (user == null || user.status() != 200) {
            return new ApiResponse(401, "User not authenticated", null);
        }
        Refund refund = new Refund();
        refund.setRefundId(UUID.randomUUID().toString());
        refund.setPaymentId(refundRequest.paymentId());
        refund.setUserId(refundRequest.userId());
        refund.setOrderId(refundRequest.orderId());
        refund.setProductId(refundRequest.productId());
        refund.setStatus("REQUESTED");
        refund.setCurrencyCode(refundRequest.currencyCode());
        refund.setTotalAmount(refundRequest.totalAmount());
        refund.setRefundReason(refundRequest.refundReason());
        refund.setCreatedAt(System.currentTimeMillis());
        refund.setProcessedAt(0);

        refundRepository.save(refund);

        publishKafkaEvent(refundRequest.userEmail(), "refund-initiated");
        return new ApiResponse(200, "Refund Request Created Successfully", null);
    }

    public ApiResponse getRefundByOrderAndProductId(String orderId, String productId) {
        Refund refund = refundRepository.findByOrderIdAndProductId(orderId, productId);
        if (refund == null) {
            return new ApiResponse(404, "Refund not found", null);
        }
        List<Refund> refundList = Collections.singletonList(refund);
        return new ApiResponse(200, "Success", refundList);
    }

    public ApiResponse processRefund(String refundId, String merchantId, String userEmail) {
        ApiResponse user = userClient.authenticateUser(merchantId, "accountType", "MERCHANT");
        if (user == null || user.status() != 200) {
            return new ApiResponse(401, "User not authenticated", null);
        }
        Refund refund = refundRepository.findByRefundId(refundId);
        if (refund == null) {
            return new ApiResponse(404, "Refund not found", null);
        }
        refund.setStatus("PROCESSED");
        refund.setProcessedAt(System.currentTimeMillis());
        refundRepository.save(refund);

        publishKafkaEvent(userEmail, "refund-completed");
        return new ApiResponse(200, "Refund Processed Successfully", null);
    }

    @Transactional
    public void deleteAllRefunds() {
        refundRepository.deleteAll();
    }

    public void publishKafkaEvent(String userEmail, String topic) {
        try {
            kafkaTemplate.send(topic, userEmail);
        } catch (Exception e) {
            log.error("Error publishing order placed: {}", e.getMessage());
        }
    }
}
