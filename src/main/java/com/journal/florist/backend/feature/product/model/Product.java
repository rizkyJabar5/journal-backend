/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.product.model;

import com.journal.florist.backend.feature.utils.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigInteger;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product extends BaseEntity {

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Name of product is required")
    private String productName;

    private String picture;

    @Lob
    private String description;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH},
            targetEntity = Category.class)
    private Category category;

    @Column(name = "price")
    private BigInteger price;

    @Override
    public int hashCode() {
        return Objects.hash(getProductName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof Product product) || !super.equals(o)) return false;
        return Objects.equals(getPublicKey(), product.getPublicKey());
    }

    @Override
    protected boolean canEqual(Object other) {
        return other instanceof Product;
    }
}
