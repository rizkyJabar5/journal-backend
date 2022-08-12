/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.product.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigInteger;

@Data
public class AddProductRequest {

    @NotBlank(message = "Product name is required")
    private String productName;

    @NotBlank(message = "Product must have category")
    private String categoryKey;
    private String description;
    private BigInteger price;
    private String picture;
}
