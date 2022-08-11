/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.order.model;

import com.journal.florist.backend.feature.customer.model.Customers;
import com.journal.florist.backend.feature.order.enums.OrderStatus;
import com.journal.florist.backend.feature.order.enums.PaymentStatus;
import com.journal.florist.backend.feature.utils.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Orders extends BaseEntity {

    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            targetEntity = Customers.class)
    @JoinColumn(foreignKey = @ForeignKey(name = "customer_fk_id"))
    private Customers customer;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToOne(mappedBy = "order")
    private OrderProductDetails orderProductDetails;

    @OneToOne(mappedBy = "order")
    private OrderShipments orderShipments;

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Orders orders) || !super.equals(o)) return false;
        return Objects.equals(getPublicKey(), orders.getPublicKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPublicKey());
    }
}
