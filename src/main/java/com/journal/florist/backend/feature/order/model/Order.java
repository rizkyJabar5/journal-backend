/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.order.model;

import com.journal.florist.backend.feature.order.enums.PaymentStatus;
import com.journal.florist.backend.feature.product.model.Product;
import com.journal.florist.backend.feature.utils.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Order extends BaseEntity {

    private String orderType;

    private BigDecimal amount;

    private PaymentStatus status;

    private Instant deliveryDate;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "product_fk_id"))
    private Product product;
}
