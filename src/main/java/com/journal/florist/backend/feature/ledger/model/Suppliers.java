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
@JsonIgnoreProperties(value ={"id", "totalDebt"}, allowGetters = true )
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
