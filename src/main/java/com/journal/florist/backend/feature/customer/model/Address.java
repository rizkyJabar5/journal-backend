/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.customer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Address {

    private String street;
    private String city;
    private String state;
    private String zip;

}
