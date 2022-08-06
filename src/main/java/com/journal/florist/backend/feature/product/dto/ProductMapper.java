/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.product.dto;

import com.journal.florist.app.utils.DateConverter;
import com.journal.florist.backend.feature.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ProductMapper {

    private String productKey;
    private String picture;
    private String productName;
    private String categoryKey;
    private String categoryName;
    private String categoryDescription;
    private String productDescription;
    private BigInteger price;
    private String createdBy;
    private String createdAt;
    private String updatedBy;
    private String updatedAt;

    public ProductMapper productMapper(Product entity) {

        LocalDateTime dateCreateAt = DateConverter.toLocalDate(entity.getCreatedAt());
        String formatUpdateAt = null;
        if (entity.getLastModifiedDate() != null) {
            LocalDateTime dateUpdateAt = DateConverter.toLocalDate(entity.getLastModifiedDate());
            formatUpdateAt = DateConverter.formatDate().format(dateUpdateAt);
        }
        String formatCreateAt = DateConverter.formatDate().format(dateCreateAt);
        String updateBy = entity.getLastModifiedBy();
        if(updateBy == null) {
            this.updatedBy = null;
        }
        return ProductMapper.builder()
                .productKey(entity.getPublicKey())
                .picture(entity.getPicture())
                .productName(entity.getProductName())
                .categoryKey(entity.getCategory().getPublicKey())
                .categoryName(entity.getCategory().getNameCategory())
                .categoryDescription(entity.getCategory().getDescription())
                .productDescription(entity.getDescription())
                .price(entity.getPrice())
                .createdBy(entity.getCreatedBy())
                .createdAt(formatCreateAt)
                .createdBy(entity.getCreatedBy())
                .updatedAt(formatUpdateAt)
                .updatedBy(updateBy)
                .build();
    }
}
