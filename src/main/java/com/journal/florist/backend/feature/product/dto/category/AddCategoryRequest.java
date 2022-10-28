package com.journal.florist.backend.feature.product.dto.category;

public record AddCategoryRequest(
        String categoryName,
        String description
) {
}
