/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Address implements Serializable {

    private String street;
    private String city;
    private String state;
    private String zip;

}
