/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.ledger.model;

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
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "order_fk_id"))
    private Orders orders = new Orders();

    private BigDecimal totalOrderAmount;
    private BigDecimal totalOrderCostPrice;
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
