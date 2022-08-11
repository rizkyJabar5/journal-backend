/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.product.service;

import com.journal.florist.app.utils.BaseResponse;
import com.journal.florist.app.utils.HasLogger;
import com.journal.florist.backend.feature.product.dto.AddProductRequest;
import com.journal.florist.backend.feature.product.dto.ProductMapper;
import com.journal.florist.app.utils.SuccessResponse;
import com.journal.florist.backend.feature.product.dto.UpdateProductRequest;
import com.journal.florist.backend.feature.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService extends HasLogger {

    Page<ProductMapper> findAllProduct(Pageable pageable);

    Product findByProductKey(String productKey);
    ProductMapper getProductByKey(String productKey);

    BaseResponse addNewProduct(AddProductRequest request);

    BaseResponse updateProduct(UpdateProductRequest request);

    SuccessResponse deleteProductById(String productKey);

    long countAllProduct();

}
