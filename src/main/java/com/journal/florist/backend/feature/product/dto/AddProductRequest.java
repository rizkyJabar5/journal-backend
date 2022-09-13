/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.product.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
public class AddProductRequest {

    @NotBlank(message = "Product name is required")
    private String productName;

    @NotBlank(message = "Product must have category")
    private String categoryKey;
    private String description;
    private BigDecimal costPrice;
    private BigDecimal price;
}
