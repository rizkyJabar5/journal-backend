/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.product.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class UpdateProductRequest {
    private String productKey;
    private String productName;
    private String description;
    private BigInteger price;
    private String categoryKey;
    private String picture;
}
