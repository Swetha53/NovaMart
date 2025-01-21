package com.novamart.product_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Document(value = "product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Product {
    @Id
    private String product_id;
    private String name;
    private String description;
    private BigDecimal price;
    private String currency_code;
    private Object categories;
    private Object reviews;
    private Timestamp created_at;
    private Timestamp updated_at;
    private String status;
    private Array attributes;
}
