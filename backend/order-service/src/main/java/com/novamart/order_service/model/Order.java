package com.novamart.order_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "n_orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    private String orderId;
    private String userId;
    private String status;
    private BigDecimal totalAmount;
    private String currencyCode;
    private String customerName;
    private long createdAt;
    private long updatedAt;
}
