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

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Collection;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Customers extends BaseEntity {

    private String name;

    private Integer phoneNumber;

    @OneToMany(mappedBy = "customers")
    private Collection<HistoryOrder> historyOrder;

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
