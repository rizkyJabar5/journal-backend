/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.order.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.journal.florist.backend.feature.customer.model.Customers;
import com.journal.florist.backend.feature.order.enums.OrderStatus;
import com.journal.florist.backend.feature.order.enums.PaymentStatus;
import com.journal.florist.backend.feature.utils.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Orders extends BaseEntity {

    @ManyToOne(
            fetch = FetchType.LAZY,
            targetEntity = Customers.class)
    @JoinColumn(foreignKey = @ForeignKey(name = "customer_fk_id"))
    private Customers customer;

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pk.order")
    private Set<OrderDetails> orderDetails = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "order", optional = false)
    @PrimaryKeyJoinColumn
    private OrderShipments orderShipment;

    public BigDecimal getTotalOrderAmount() {
        BigInteger TWO = new BigInteger("2");

        Set<OrderDetails> orderProduct = getOrderDetails();
        BigInteger total = orderProduct.parallelStream()
                .map(OrderDetails::getTotalPrice)
                .filter(n -> n.mod(TWO).equals(BigInteger.ZERO))
                .reduce(BigInteger.ZERO, BigInteger::add);

        return new BigDecimal(total);

    }

    public Orders(Customers customer,
                  PaymentStatus paymentStatus,
                  OrderStatus orderStatus,
                  String createdBy,
                  Date createdAt) {
        this.customer = customer;
        this.paymentStatus = paymentStatus;
        this.orderStatus = orderStatus;
        this.setCreatedBy(createdBy);
        this.setCreatedAt(createdAt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Orders orders) || !super.equals(o)) return false;
        return Objects.equals(getPublicKey(), orders.getPublicKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPublicKey());
    }
}
