/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.product.dto.product;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
public class AddProductRequest {
    @NotBlank(message = "Product name is required")
    private String productName;
    private String description;
    private Integer stock;
    private BigDecimal price;
    private Integer weight;
    private String material;
    private BigDecimal materialPrice;
}
