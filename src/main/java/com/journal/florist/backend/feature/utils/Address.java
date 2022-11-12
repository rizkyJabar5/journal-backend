/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@JsonIgnoreProperties(value = "fullAddress", allowGetters = true)
public class Address implements Serializable {

    @NotEmpty(message = "Street may not be empty")
    private String street;

    @NotEmpty(message = "City may not be empty")
    private String city;

    @NotEmpty(message = "Your state may not be empty")
    private String province;

    @NotEmpty(message = "Country is may not be empty")
    private String country;

    @NotEmpty(message = "Zip address may not be empty")
    private String zip;

    @Transient
    public String getFullAddress() {
        return street + ", " + city + ", " + province + ", " + country + ", " + zip;
    }
}
