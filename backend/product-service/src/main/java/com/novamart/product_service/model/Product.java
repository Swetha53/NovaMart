package com.novamart.product_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
    private List<String> categories;
    private List<Reviews> reviews;
    private long created_at;
    private long updated_at;
    private String status;
    private Object attributes;
}
