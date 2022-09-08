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
    @Column(nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.NOT_YET_PAID;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "order", optional = false)
    @PrimaryKeyJoinColumn
    private OrderShipments orderShipment;

    @OneToOne(mappedBy = "order", optional = false)
    private Payments payment;

    public BigDecimal getTotalOrderAmount() {
        Set<OrderDetails> orderProduct = getOrderDetails();

        return orderProduct.parallelStream()
                .map(OrderDetails::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalOrderCostPrice() {

        return getOrderDetails().parallelStream()
                .map(OrderDetails::getTotalCostPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Orders(Customers customer,
//                  PaymentStatus paymentStatus,
                  OrderStatus orderStatus,
                  String createdBy,
                  Date createdAt) {
        this.customer = customer;
//        this.paymentStatus = paymentStatus;
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
