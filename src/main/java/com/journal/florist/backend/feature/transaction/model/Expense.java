/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.transaction.model;

import com.journal.florist.backend.feature.order.enums.PaymentStatus;
import com.journal.florist.backend.feature.utils.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigInteger;

@Getter
@Setter
@Entity
public class Expense extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Min(1)
    private Integer quantity = 1;
    private BigInteger price;
    private BigInteger totalAmount;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Suppliers.class)
    private Suppliers supplier;

    public BigInteger getTotalAmount() {
        return this.totalAmount = this.price.multiply(BigInteger.valueOf(this.quantity));
    }
}


