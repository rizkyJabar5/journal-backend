/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.customer.model;

import com.journal.florist.backend.feature.order.model.HistoryOrder;
import com.journal.florist.backend.feature.utils.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.Objects;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Customers extends BaseEntity {

    private String name;
    private String phoneNumber;

    @Embedded
    private Company company;

    @OneToMany(mappedBy = "customers")
    private Collection<HistoryOrder> historyOrder;
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
