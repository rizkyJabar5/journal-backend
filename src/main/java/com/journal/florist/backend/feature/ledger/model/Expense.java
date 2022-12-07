/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.ledger.model;

import com.journal.florist.backend.feature.ledger.enums.Pay;
import com.journal.florist.backend.feature.utils.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Expense extends BaseEntity {

    private String additionalInformation;
    private BigDecimal amount;

    @Column(nullable = false)
    private Pay payFor;

}


