package com.novamart.order_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "n_status_history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatusHistory {
    @Id
    private String statusHistoryId;
    private String orderId;
    private String oldStatus;
    private String newStatus;
    private long changedAt;
}
