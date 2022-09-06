/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.ledger.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Suppliers {

    @Id
    private String id;

    @Column(unique = true, nullable = false)
    private String supplierName;

    private BigDecimal totalDebt = BigDecimal.ZERO;

    @PrePersist
    public void prePersist() {
        if(id != null) {
            return;
        }
        setId(RandomStringUtils.randomAlphanumeric(8));
    }

}
