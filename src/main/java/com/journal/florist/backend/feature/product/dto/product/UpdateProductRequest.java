/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.product.dto.product;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class UpdateProductRequest implements Serializable {
    @NotBlank(message = "Product id is required")
    private String productId;

    private String productName;
    private String description;
    private Integer stock;
    private BigDecimal price;
    private String categoryId;
    private Float weight;
    private String material;
    private BigDecimal materialPrice;
}
