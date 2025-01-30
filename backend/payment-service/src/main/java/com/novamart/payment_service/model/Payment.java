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
@Table(name = "n_payment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    private String paymentId;
    private String orderId;
    private String userId;
    private String status;
    private String currencyCode;
    private BigDecimal totalAmount;
    private String paymentMethod;
    private long createdAt;
    private long updatedAt;
}
