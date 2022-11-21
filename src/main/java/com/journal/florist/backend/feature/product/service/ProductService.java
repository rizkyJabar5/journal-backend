/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.product.service;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.app.common.utils.HasLogger;
import com.journal.florist.backend.feature.product.dto.product.AddProductRequest;
import com.journal.florist.backend.feature.product.dto.product.ProductMapper;
import com.journal.florist.app.common.messages.SuccessResponse;
import com.journal.florist.backend.feature.product.dto.product.UpdateProductRequest;
import com.journal.florist.backend.feature.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService extends HasLogger {

    Page<ProductMapper> getAllProduct(Pageable pageable);
    ProductMapper getProductById(String productId);
    Product findByProductId(String productKey);
    List<ProductMapper> getProductName(String productName);
    Page<ProductMapper> getByField(Pageable pageable);
    BaseResponse addNewProduct(AddProductRequest request, MultipartFile image);

    BaseResponse updateProduct(UpdateProductRequest request, MultipartFile image);

    SuccessResponse deleteProductById(String productKey);

    long countAllProduct();

}
