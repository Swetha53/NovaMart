package com.novamart.product_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(value = "review")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Reviews {
    @Id
    private String reviewId;
    private String userId;
    private String merchantId;
    private String productId;
    private String title;
    private String comment;
    private List<String> imageUrl;
    private int rating;
}
