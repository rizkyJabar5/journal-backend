/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Lob;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Address implements Serializable {

    @Lob
    private String street;
    private String city;
    private String province;
    private String country;
    private String zip;

}
