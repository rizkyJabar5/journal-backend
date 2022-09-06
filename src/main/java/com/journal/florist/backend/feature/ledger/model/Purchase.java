/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.ledger.model;

import com.journal.florist.backend.feature.utils.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "purchase")
@Entity
public class Purchase extends BaseEntity {

    @ManyToOne
    private Suppliers suppliers;
    private String productName;
    private BigDecimal price;
    private Integer quantity;

    @Column(insertable = false)
    private BigDecimal total;

    public BigDecimal setTotalPurchase() {
        return this.total = price.multiply(BigDecimal.valueOf(quantity));
    }

    public BigDecimal addDebt() {
        return suppliers.getTotalDebt().add(setTotalPurchase());
    }
}
