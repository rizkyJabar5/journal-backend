/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.product.model;

import com.journal.florist.backend.feature.utils.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Entity
public class Product extends BaseEntity {

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Name of product is required")
    private String productName;

    private String picture;

    @Lob
    private String description;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "weight")
    private Long weight;

    @Column(name = "material")
    private String material;

    @Column(name = "material_price")
    private BigDecimal materialPrice;

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
