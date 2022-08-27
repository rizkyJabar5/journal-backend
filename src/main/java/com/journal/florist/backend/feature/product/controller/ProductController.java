/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.product.controller;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.backend.feature.product.dto.AddProductRequest;
import com.journal.florist.backend.feature.product.dto.ProductMapper;
import com.journal.florist.app.common.messages.SuccessResponse;
import com.journal.florist.backend.feature.product.dto.UpdateProductRequest;
import com.journal.florist.backend.feature.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.Optional;

import static com.journal.florist.app.constant.ApiUrlConstant.PRODUCTS_URL;

@RequiredArgsConstructor
@RestController
@RequestMapping(PRODUCTS_URL)
@RolesAllowed("owner")
public class ProductController {

    private final ProductService service;

    @GetMapping("/")
    public ResponseEntity<Page<ProductMapper>> getAllProduct(Pageable pageable) {

        Page<ProductMapper> response = service.findAllProduct(pageable);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<ProductMapper> getProductByKey(@RequestParam(name = "key") String productKey) {

        Optional<ProductMapper> product = Optional.ofNullable(service.getProductByKey(productKey));
        if (product.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok().body(product.get());
    }

    @PostMapping("/add-product")
    public ResponseEntity<BaseResponse> createProduct(
            @Valid @ModelAttribute AddProductRequest request) {
        BaseResponse response= service.addNewProduct(request);

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update")
    public ResponseEntity<BaseResponse> updateProduct(
            @Valid @ModelAttribute UpdateProductRequest request) {
        BaseResponse response = service.updateProduct(request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<SuccessResponse> deleteProduct(@RequestParam(name = "key") String productKey) {
        SuccessResponse response = service.deleteProductById(productKey);
        return ResponseEntity.accepted().body(response);
    }

}
