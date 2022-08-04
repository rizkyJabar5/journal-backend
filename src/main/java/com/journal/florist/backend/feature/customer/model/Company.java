/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.customer.model;

import com.journal.florist.backend.feature.utils.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Company extends BaseEntity {

    private String companyName;

    @Embedded
    private Address address;

}
