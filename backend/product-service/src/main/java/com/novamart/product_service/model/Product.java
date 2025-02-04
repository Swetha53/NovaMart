package com.novamart.product_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Document(value = "product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Product {
    @Id
    private String productId;
    private String merchantId;
    private String name;
    private List<String> images;
    private String description;
    private BigDecimal price;
    private String currencyCode;
    private List<String> categories;
    private List<Reviews> reviews;
    private long createdAt;
    private long updatedAt;
    private String status;
    private Object attributes;
}
