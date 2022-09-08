/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.customer.model;

import com.journal.florist.backend.feature.ledger.model.Sales;
import com.journal.florist.backend.feature.utils.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Customers extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Embedded
    private Company company;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "customers")
    private Set<Sales> sales;

    @OneToOne(mappedBy = "customer",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @PrimaryKeyJoinColumn
    private CustomerDebt customerDebt;

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Customers customers) || super.equals(obj)) return false; // null or other class
        return Objects.equals(getName(), customers.name);
    }

}
