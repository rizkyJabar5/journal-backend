/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.product.dto;

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
    private BigDecimal costPrice;
    private BigDecimal price;
    private String categoryId;
}
