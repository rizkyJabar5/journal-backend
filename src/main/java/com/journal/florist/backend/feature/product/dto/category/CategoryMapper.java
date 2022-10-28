package com.journal.florist.backend.feature.product.dto.category;

import com.journal.florist.backend.feature.product.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
@Component
public class CategoryMapper implements Serializable {
    private String categoryId;

    @NotBlank(message = "Category name is required")
    private String nameCategory;

    private String description;
    private Date createdAt;
    private String createdBy;
    private String lastModifiedBy;
    private Date lastModifiedDate;

    public CategoryMapper categoryMapper(Category entity){

        return builder()
                .categoryId(entity.getPublicKey())
                .nameCategory(entity.getNameCategory())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .lastModifiedBy(entity.getLastModifiedBy())
                .lastModifiedDate(entity.getLastModifiedDate())
                .build();
    }
}