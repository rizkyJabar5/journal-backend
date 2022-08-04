/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.order.model;

import com.journal.florist.backend.feature.customer.model.Customers;
import com.journal.florist.backend.feature.order.enums.PaymentStatus;
import com.journal.florist.backend.feature.product.model.Product;
import com.journal.florist.backend.feature.utils.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
public class HistoryOrder extends BaseEntity {

    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            targetEntity = Customers.class
    )
    @JoinColumn(foreignKey = @ForeignKey(name = "customer_pk"))
    private Customers customers;

    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            targetEntity = Product.class
    )
    @JoinColumn(foreignKey = @ForeignKey(name = "buy_product_pk"))
    private Product product;

    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    @Override
    public int hashCode() {
        if (getId() != null) {
            return getId().hashCode();
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BaseEntity other)) {
            return false; // null or other class
        }

        if (getId() != null) {
            return getId().equals(other.getId());
        }
        return super.equals(other);
    }

}
