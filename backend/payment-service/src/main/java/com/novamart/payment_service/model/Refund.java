package com.novamart.payment_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "n_refund")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Refund {
    @Id
    private String refundId;
    private String paymentId;
    private String userId;
    private String orderId;
    private String productId;
    private String status;
    private String currencyCode;
    private BigDecimal totalAmount;
    private String refundReason;
    private long createdAt;
    private long processedAt;
}
