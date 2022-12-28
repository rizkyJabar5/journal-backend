/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.ledger.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties(value ={"id", "totalDebt"}, allowGetters = true )
public class Suppliers {

    @Id
    private String id;

    @Column(unique = true, nullable = false)
    private String supplierName;

    private BigDecimal totalDebt = BigDecimal.ZERO;

    @Temporal(TemporalType.TIMESTAMP)
    private Date payDebtDate;
    private BigDecimal amountPay = BigDecimal.ZERO;
    @PrePersist
    public void prePersist() {
        if(id != null) {
            return;
        }
        setId(RandomStringUtils.randomAlphanumeric(8));
    }

}
