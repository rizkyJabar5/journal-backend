/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.product.service;


import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.app.common.messages.SuccessResponse;
import com.journal.florist.app.security.SecurityUtils;
import com.journal.florist.backend.exceptions.AppBaseException;
import com.journal.florist.backend.exceptions.IllegalException;
import com.journal.florist.backend.exceptions.NotFoundException;
import com.journal.florist.backend.feature.product.dto.AddProductRequest;
import com.journal.florist.backend.feature.product.dto.ProductMapper;
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
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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
    public Page<ProductMapper> getAllProduct(Pageable pageable) {
        return repository.findAllProduct(pageable)
                .map(productMapper::productMapper);
    }

    @Override
    public ProductMapper getProductByKey(String productKey) {
        return repository.findByPublicKey(productKey)
                .map(productMapper::productMapper)
                .orElseThrow(() ->
                        new AppBaseException(String.format(NOT_FOUND_MSG, EntityUtil.getName(Product.class), productKey)));
    }

    @Override
    public Product findByProductKey(String productKey) {
        return repository.findByPublicKey(productKey)
                .orElseThrow(() ->
                        new NotFoundException(String.format(NOT_FOUND_MSG, EntityUtil.getName(Product.class), productKey)));
    }

    @Override
    public List<ProductMapper> getProductName(String productName) {
        return repository.findByProductName(productName)
                .parallelStream()
                .map(productMapper::productMapper)
                .toList();
    }

    @Override
    public Page<ProductMapper> getByField(Pageable pageable) {
        return null;
    }

    @Override
    public BaseResponse addNewProduct(AddProductRequest request) {
        Authentication authentication = SecurityUtils.getAuthentication();
        String createdBy = authentication.getName();

        boolean authenticated = SecurityUtils.isAuthenticated();
        Product product = new Product();

        if (authenticated) {
            boolean persisted = repository.existsByProductName(request.getProductName());
            if (persisted) {
                throw new IllegalException(String.format(MUST_BE_UNIQUE, EntityUtil.getName(Product.class)));
            }
            product.setProductName(request.getProductName());
            Category category = categoryService.findByCategoryKey(request.getCategoryKey());

            product.setCategory(category);
            product.setDescription(request.getDescription());
            product.setCreatedBy(createdBy);
            product.setCreatedAt(new Date(System.currentTimeMillis()));

            if(lessThanCostPrice(request.getPrice(), request.getCostPrice())) {
                throw new AppBaseException("Price must greater than cost price");
            }
            product.setCostPrice(request.getCostPrice());
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
        Product product = findByProductKey(request.getProductId());

        if (authenticated) {
            if (Objects.nonNull(request.getProductName())) {
                boolean persisted = repository.existsByProductName(product.getProductName());
                if (persisted) {
                    throw new IllegalException(String.format(MUST_BE_UNIQUE, EntityUtil.getName(Product.class)));
                }
                product.setProductName(request.getProductName());
            }
            if (Objects.nonNull(request.getCategoryId())) {
                Category category = categoryService.findByCategoryKey(request.getCategoryId());
                product.setCategory(category);
            }
            if (Objects.nonNull(request.getDescription())) {
                product.setDescription(request.getDescription());
            }
            if (Objects.nonNull(request.getCostPrice())) {
                product.setCostPrice(request.getCostPrice());
            }
            if(lessThanCostPrice(request.getPrice(), request.getCostPrice())) {
                throw new AppBaseException("Price must greater than cost price");
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
                SuccessResponse.StatusOperation.SUCCESS);
    }

    @Override
    public long countAllProduct() {
        return repository.count();
    }

    private boolean lessThanCostPrice(BigDecimal price, BigDecimal costPrice) {

        return price.compareTo(costPrice) <= 0;
    }
}
