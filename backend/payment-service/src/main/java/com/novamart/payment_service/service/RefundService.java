package com.novamart.payment_service.service;

import com.novamart.payment_service.dto.RefundRequest;
import com.novamart.payment_service.model.Refund;
import com.novamart.payment_service.respository.RefundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefundService {
    private final RefundRepository refundRepository;

    public void initiateRefund(RefundRequest refundRequest) {
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
    }

    public Refund getRefundByOrderAndProductId(String orderId, String productId) {
        return refundRepository.findByOrderIdAndProductId(orderId, productId);
    }

    public void processRefund(String refundId) {
        Refund refund = refundRepository.findByRefundId(refundId);
        if (refund == null) {
            throw new RuntimeException("Refund not found");
        }
        refund.setStatus("PROCESSED");
        refund.setProcessedAt(System.currentTimeMillis());
        refundRepository.save(refund);
    }

    @Transactional
    public void deleteAllRefunds() {
        refundRepository.deleteAll();
    }
}
