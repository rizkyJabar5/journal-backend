/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.ledger.model;

import com.journal.florist.backend.feature.customer.model.Customers;
import com.journal.florist.backend.feature.order.model.Orders;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Setter
@Getter
@Entity
public class Sales {

    @Id
    @SequenceGenerator(name = "saleSequence",
            sequenceName = "sale_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = " saleSequence")
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "The id of the entity is required")
    private String salesId;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            targetEntity = Orders.class)
    @JoinColumn(foreignKey = @ForeignKey(name = "order_fk_id"))
    private Orders orders;

    private BigDecimal totalOrderAmount;
    private BigDecimal totalOrderCostPrice;

    @ManyToOne(
            fetch = FetchType.EAGER,
            targetEntity = Customers.class)
    @JoinColumn(foreignKey = @ForeignKey(name = "customer_fk_id"))
    private Customers customers;

    private BigDecimal salesAmount = BigDecimal.ZERO;
    private BigDecimal netProfit = BigDecimal.ZERO;

    @Temporal(TemporalType.TIMESTAMP)
    private Date saleDate;

    private BigDecimal monthlySale;

    @Override
    public int hashCode() {
        if (getId() != null) {
            return getId().hashCode();
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Sales other)) {
            return false; // null or other class
        }

        if (getSalesId() != null) {
            return getId().equals(other.getId());
        }
        return super.equals(other);
    }

    @PrePersist
    private void prePersist() {
        if (Objects.isNull(getSalesId())) {
            setSalesId(UUID.randomUUID().toString().replaceAll("-", ""));
        }
    }

}
