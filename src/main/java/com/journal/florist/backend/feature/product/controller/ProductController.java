/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.product.controller;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.app.common.messages.SuccessResponse;
import com.journal.florist.backend.feature.product.dto.AddProductRequest;
import com.journal.florist.backend.feature.product.dto.ProductMapper;
import com.journal.florist.backend.feature.product.dto.UpdateProductRequest;
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
    @GetMapping(value = "/")
    public ResponseEntity<Page<ProductMapper>> getAllProduct(
            @RequestParam(defaultValue = "1",
                    required = false) Integer page,
            @RequestParam(defaultValue = "10",
                    required = false) Integer limit) {
        Pageable pageable = FilterableCrudService.getPageable(page - 1, limit);
        Page<ProductMapper> response = service.getAllProduct(pageable);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Filter by product name")
    @GetMapping(value = "/name")
    public ResponseEntity<Object> getProductByName(@RequestParam(name = "product") String productName) {

        List<ProductMapper> mapper = service.getProductName(productName);
        return new ResponseEntity<>(mapper, HttpStatus.FOUND);
    }

    @Operation(summary = "Filter product by product key in request param")
    @GetMapping(value = "")
    public ResponseEntity<ProductMapper> getProductByKey(@RequestParam(name = "key") String productKey) {

        Optional<ProductMapper> product = Optional.ofNullable(service.getProductByKey(productKey));
        if (product.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok().body(product.get());
    }

    @Operation(summary = "Add new product")
    @PostMapping(value = "/add-product",
            consumes = {
                    MediaType.MULTIPART_FORM_DATA_VALUE,
                    MediaType.APPLICATION_JSON_VALUE
            },
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> createProduct(
            @Valid @ModelAttribute AddProductRequest request,
            @RequestParam(required = false) MultipartFile image) {
        BaseResponse response = service.addNewProduct(request, image);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Update product")
    @PutMapping(value = "/update",
            consumes = {
                    MediaType.MULTIPART_FORM_DATA_VALUE,
                    MediaType.APPLICATION_JSON_VALUE
            },
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> updateProduct(
            @Valid @ModelAttribute UpdateProductRequest request,
            @RequestParam(required = false) MultipartFile image) {
        BaseResponse response = service.updateProduct(request, image);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Delete product")
    @DeleteMapping(value = "/delete",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessResponse> deleteProduct(@RequestParam(name = "key") String productKey) {
        SuccessResponse response = service.deleteProductById(productKey);
        return ResponseEntity.accepted().body(response);
    }

}
