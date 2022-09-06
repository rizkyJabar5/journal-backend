package com.journal.florist.backend.feature.ledger.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PurchaseRequest implements Serializable {

    private String supplierId;
    private String productName;
    private BigDecimal price;
    private Integer quantity;

}
