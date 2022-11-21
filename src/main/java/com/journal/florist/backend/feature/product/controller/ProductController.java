/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.product.controller;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.app.common.messages.SuccessResponse;
import com.journal.florist.backend.feature.product.dto.product.AddProductRequest;
import com.journal.florist.backend.feature.product.dto.product.ProductMapper;
import com.journal.florist.backend.feature.product.dto.product.UpdateProductRequest;
import com.journal.florist.backend.feature.product.service.ProductService;
import com.journal.florist.backend.feature.utils.FilterableCrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.journal.florist.app.constant.ApiUrlConstant.PRODUCTS_URL;

@Tag(name = "Product Endpoint",
        description = "Transaction for product")
@RequiredArgsConstructor
@RestController
@RequestMapping(PRODUCTS_URL)
@RolesAllowed("owner")
public class ProductController {

    private final ProductService service;

    @Operation(summary = "Fetching all product with pagination")
    @GetMapping
    public ResponseEntity<BaseResponse> getAllProduct(
            @RequestParam(defaultValue = "1",
                    required = false) Integer page,
            @RequestParam(defaultValue = "10",
                    required = false) Integer limit) {
        Pageable pageable = FilterableCrudService.getPageable(page - 1, limit);
        Page<ProductMapper> productMapper = service.getAllProduct(pageable);

        BaseResponse response = new BaseResponse(
                HttpStatus.OK,
                "Fetching all product",
                productMapper
        );

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Filter by product name")
    @GetMapping(value = "/product")
    public ResponseEntity<BaseResponse> getProductByName(@RequestParam(name = "name") String productName) {

        List<ProductMapper> productMapper = service.getProductName(productName);

        BaseResponse response = new BaseResponse(
                HttpStatus.FOUND,
                "Fetching product by name",
                productMapper
        );

        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @Operation(summary = "Filter product by product key in request param")
    @GetMapping(value = "/{id}")
    public ResponseEntity<BaseResponse> getProductByKey(@PathVariable("id") String productId) {

        Optional<ProductMapper> productMapper = Optional.ofNullable(service.getProductById(productId));
        if (productMapper.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        BaseResponse response = new BaseResponse(
                HttpStatus.FOUND,
                String.format("Customer found with id %s", productId),
                productMapper
        );

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Add new product")
    @PostMapping(value = "/add-product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse> createProduct(
            @Valid @ModelAttribute AddProductRequest request,
            @RequestParam(required = false) MultipartFile image) {
        BaseResponse response = service.addNewProduct(request, image);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Update product")
    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse> updateProduct(
            @Valid @ModelAttribute UpdateProductRequest request,
            @RequestParam(required = false) MultipartFile image) {
        BaseResponse response = service.updateProduct(request, image);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Delete product")
    @DeleteMapping(value = "/delete")
    public ResponseEntity<SuccessResponse> deleteProduct(@RequestParam(name = "key") String productKey) {
        SuccessResponse response = service.deleteProductById(productKey);
        return ResponseEntity.accepted().body(response);
    }

}
