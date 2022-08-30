package com.journal.florist.backend.feature.product.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class CategoryRequest implements Serializable {

    private String categoryId;
    @NotBlank(message = "Category name is required")
    private String categoryName;
    private String description;

}
