/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.product.dto.product;

import com.journal.florist.app.common.utils.converter.DateConverter;
import com.journal.florist.backend.feature.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ProductMapper {

    private String productId;
    private String picture;
    private String productName;
    private String categoryKey;
    private String categoryName;
    private String categoryDescription;
    private String productDescription;
    private BigDecimal costPrice;
    private BigDecimal price;
    private String createdBy;
    private String createdAt;
    private String updatedBy;
    private String updatedAt;

    public ProductMapper productMapper(Product entity) {

        LocalDateTime dateCreateAt = DateConverter.toLocalDateTime(entity.getCreatedAt());
        String formatUpdateAt = null;
        if (entity.getLastModifiedDate() != null) {
            LocalDateTime dateUpdateAt = DateConverter.toLocalDateTime(entity.getLastModifiedDate());
            formatUpdateAt = DateConverter.formatDateTime().format(dateUpdateAt);
        }
        String formatCreateAt = DateConverter.formatDateTime().format(dateCreateAt);
        String updateBy = entity.getLastModifiedBy();
        if(updateBy == null) {
            this.updatedBy = null;
        }
        return ProductMapper.builder()
                .productId(entity.getPublicKey())
                .picture(entity.getPicture())
                .productName(entity.getProductName())
                .categoryKey(entity.getCategory().getPublicKey())
                .categoryName(entity.getCategory().getNameCategory())
                .categoryDescription(entity.getCategory().getDescription())
                .productDescription(entity.getDescription())
                .costPrice(entity.getCostPrice())
                .price(entity.getPrice())
                .createdBy(entity.getCreatedBy())
                .createdAt(formatCreateAt)
                .createdBy(entity.getCreatedBy())
                .updatedAt(formatUpdateAt)
                .updatedBy(updateBy)
                .build();
    }
}
