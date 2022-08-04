/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.product.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class AddProductRequest {

    private String productName;
    private String categoryKey;
    private String description;
    private BigInteger price;
    private String picture;
}
