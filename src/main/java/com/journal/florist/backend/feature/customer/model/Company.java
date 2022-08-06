/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.customer.model;

import com.journal.florist.backend.feature.utils.Address;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class Company implements Serializable {

    private String companyName;

    @Embedded
    private Address address;
}
