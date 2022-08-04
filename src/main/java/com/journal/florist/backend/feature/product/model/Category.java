/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.product.model;

import com.journal.florist.backend.feature.utils.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Category extends BaseEntity {

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Category name is required")
    private String nameCategory;

    @Column
    private String description;

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getNameCategory());
    }

    @Override
    protected boolean canEqual(Object other) {
        return other instanceof Category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof Category category) || !super.equals(o)) return false;
        return Objects.equals(getNameCategory(), category.nameCategory);
    }
}
