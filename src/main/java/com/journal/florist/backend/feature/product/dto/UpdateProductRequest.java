/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.product.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigInteger;

@Data
public class UpdateProductRequest implements Serializable {
    @NotBlank(message = "Product id is required")
    private String productId;

    @NotBlank(message = "Product name is required")
    private String productName;

    private String description;
    private BigInteger price;

    @NotBlank(message = "Category id is required")
    private String categoryId;

    private String picture;
}
