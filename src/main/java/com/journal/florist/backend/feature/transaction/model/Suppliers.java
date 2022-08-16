/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.transaction.model;

import com.journal.florist.backend.feature.utils.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigInteger;

@Getter
@Setter
@Entity
public class Suppliers extends BaseEntity {

    @Column(unique = true)
    private String supplierName;

    @Column(unique = true)
    private String productName;
    private BigInteger price;


}
