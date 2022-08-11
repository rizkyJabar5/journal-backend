/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.product.service;


import com.journal.florist.app.security.SecurityUtils;
import com.journal.florist.app.utils.BaseResponse;
import com.journal.florist.backend.exceptions.AppBaseException;
import com.journal.florist.backend.exceptions.IllegalException;
import com.journal.florist.backend.exceptions.NotFoundException;
import com.journal.florist.backend.feature.product.dto.AddProductRequest;
import com.journal.florist.backend.feature.product.dto.ProductMapper;
import com.journal.florist.app.utils.SuccessResponse;
import com.journal.florist.backend.feature.product.dto.UpdateProductRequest;
import com.journal.florist.backend.feature.product.model.Category;
import com.journal.florist.backend.feature.product.model.Product;
import com.journal.florist.backend.feature.product.repositories.ProductRepository;
import com.journal.florist.backend.feature.utils.EntityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Objects;

import static com.journal.florist.app.constant.JournalConstants.*;
import static com.journal.florist.app.security.config.SecurityConst.MUST_BE_AUTHENTICATED;

@Transactional
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    private final ProductMapper productMapper;

    private final CategoryService categoryService;

    @Override
    public Product findByProductKey(String productKey) {
        return repository.findByPublicKey(productKey)
                .orElseThrow(() ->
                        new NotFoundException(String.format(PRODUCT_NOT_FOUND_MSG, productKey)));
    }

    @Override
    public ProductMapper getProductByKey(String productKey) {
        return repository.findByPublicKey(productKey)
                .map(productMapper::productMapper)
                .orElseThrow(() ->
                        new NotFoundException(String.format(PRODUCT_NOT_FOUND_MSG, productKey)));
    }

    @Override
    public BaseResponse addNewProduct(AddProductRequest request) {
        Authentication authentication = SecurityUtils.getAuthentication();
        String createdBy = authentication.getName();

        boolean authenticated = SecurityUtils.isAuthenticated();
        Product product = new Product();

        if (authenticated) {
            boolean persisted = repository.existsByProductName(request.getProductName());
            if(persisted){
                throw new IllegalException(String.format(MUST_BE_UNIQUE, EntityUtil.getName(Product.class)));
            }
            product.setProductName(request.getProductName());
            Category category = categoryService.findByCategoryKey(request.getCategoryKey());
            product.setCategory(category);
            product.setDescription(request.getDescription());
            product.setCreatedBy(createdBy);
            product.setCreatedAt(new Date(System.currentTimeMillis()));
            product.setPrice(request.getPrice());
            product.setPicture(request.getPicture());

            repository.save(product);
        } else {
            throw new AppBaseException(MUST_BE_AUTHENTICATED);
        }

        ProductMapper mapper = productMapper.productMapper(product);
        return new BaseResponse(
                HttpStatus.OK,
                "Successfully added product",
                mapper);
    }

    @Override
    public BaseResponse updateProduct(UpdateProductRequest request) {

        Authentication authentication = SecurityUtils.getAuthentication();
        String updateBy = authentication.getName();
        boolean authenticated = SecurityUtils.isAuthenticated();
        Product product = findByProductKey(request.getProductKey());

        if (authenticated) {
            if (Objects.nonNull(request.getProductName())) {
                boolean persisted = repository.existsByProductName(product.getProductName());
                if(persisted){
                    throw new IllegalException(String.format(MUST_BE_UNIQUE, EntityUtil.getName(Product.class)));
                }
                product.setProductName(request.getProductName());
            }
            if (Objects.nonNull(request.getCategoryKey())) {
                Category category = categoryService.findByCategoryKey(request.getCategoryKey());
                product.setCategory(category);
            }
            if (Objects.nonNull(request.getDescription())) {
                product.setDescription(request.getDescription());
            }
            if (Objects.nonNull(request.getPrice())) {
                product.setPrice(request.getPrice());
            }
            if (Objects.nonNull(request.getPicture())) {
                product.setPicture(request.getPicture());
            }

            product.setLastModifiedBy(updateBy);
            product.setLastModifiedDate(new Date(System.currentTimeMillis()));

            repository.save(product);
        }

        ProductMapper mapper = productMapper.productMapper(product);
        return new BaseResponse(
                HttpStatus.ACCEPTED,
                "Successfully updated product",
                mapper);
    }

    @Override
    public SuccessResponse deleteProductById(String productKey) {
        Product persistedProduct = findByProductKey(productKey);
        repository.delete(persistedProduct);
        return new SuccessResponse(
                String.format(SUCCESSFULLY_DELETE_OPERATION, productKey),
                SuccessResponse.StatusOperation.SUCCESS

        );
    }

    @Override
    public Page<ProductMapper> findAllProduct(Pageable pageable) {
        return repository.findAllProduct(pageable)
                .map(productMapper::productMapper);
    }

    @Override
    public long countAllProduct() {
        return repository.count();
    }
}
