package com.novamart.search_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.util.List;

@Document(indexName = "product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Product {
    @Id
    private String productId;
    @Field(type= FieldType.Text)
    private String merchantId;
    @Field(type= FieldType.Text)
    private String name;
    @Field(type= FieldType.Keyword)
    private List<String> images;
    @Field(type= FieldType.Text)
    private String description;
    @Field(type= FieldType.Double)
    private BigDecimal price;
    @Field(type= FieldType.Text)
    private String currencyCode;
    @Field(type= FieldType.Text)
    private List<String> categories;
    @Field(type= FieldType.Long)
    private long createdAt;
    @Field(type= FieldType.Long)
    private long updatedAt;
    @Field(type= FieldType.Text)
    private String status;
    @Field(type= FieldType.Nested)
    private Object attributes;
}
