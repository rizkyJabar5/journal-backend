/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.product.service;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.app.common.utils.HasLogger;
import com.journal.florist.backend.feature.product.dto.AddProductRequest;
import com.journal.florist.backend.feature.product.dto.ProductMapper;
import com.journal.florist.app.common.messages.SuccessResponse;
import com.journal.florist.backend.feature.product.dto.UpdateProductRequest;
import com.journal.florist.backend.feature.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService extends HasLogger {

    Page<ProductMapper> getAllProduct(Pageable pageable);
    ProductMapper getProductByKey(String productKey);
    Product findByProductKey(String productKey);
    List<ProductMapper> getProductName(String productName);
    Page<ProductMapper> getByField(Pageable pageable);
    BaseResponse addNewProduct(AddProductRequest request);

    BaseResponse updateProduct(UpdateProductRequest request);

    SuccessResponse deleteProductById(String productKey);

    long countAllProduct();

}
