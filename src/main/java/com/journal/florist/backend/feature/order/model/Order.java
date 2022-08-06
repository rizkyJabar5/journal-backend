/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.order.model;

import com.journal.florist.backend.feature.utils.Address;
import com.journal.florist.backend.feature.customer.model.Customers;
import com.journal.florist.backend.feature.order.enums.PaymentStatus;
import com.journal.florist.backend.feature.product.model.Product;
import com.journal.florist.backend.feature.utils.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Order extends BaseEntity {

    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            targetEntity = Customers.class)
    @JoinColumn(foreignKey = @ForeignKey(name = "customer_fk_id"))
    private Customers customer;
    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            targetEntity = Product.class)
    @JoinColumn(foreignKey = @ForeignKey(name = "product_fk_id"))
    private Product product;
    private Integer quantity;
    private BigInteger amount;
    private PaymentStatus status;

    @Embedded
    private Address deliveryAddress;
    private Instant deliveryDate;

    public BigInteger getTotalAmount() {
        return this.amount = this.product.getPrice().multiply(BigInteger.valueOf(this.quantity));
    }


    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Order order) || !super.equals(o)) return false;
        return Objects.equals(getPublicKey(), order.getPublicKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPublicKey());
    }
}
