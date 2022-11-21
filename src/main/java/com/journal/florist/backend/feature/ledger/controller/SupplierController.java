package com.journal.florist.backend.feature.ledger.controller;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.backend.feature.ledger.model.Suppliers;
import com.journal.florist.backend.feature.ledger.service.SupplierService;
import com.journal.florist.backend.feature.utils.FilterableCrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.journal.florist.app.constant.ApiUrlConstant.SUPPLIER_URL;

@Tag(name = "Supplier Endpoint",
        description = "Endpoint for all service customer")
@RequiredArgsConstructor
@RestController
@RequestMapping(SUPPLIER_URL)
public class SupplierController {

    private final SupplierService supplierService;

    @Operation(summary = "Fetching all supplier found in record")
    @GetMapping
    public ResponseEntity<BaseResponse> getAllSupplier(
            @RequestParam(name = "page",
                    required = false,
                    defaultValue = "1") int page,
            @RequestParam(name = "limit",
                    required = false,
                    defaultValue = "10") int limit) {
        Pageable filter = FilterableCrudService.getPageable(page - 1, limit);
        Page<Suppliers> suppliers = supplierService.getAllSuppliers(filter);
        if (suppliers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        BaseResponse response = new BaseResponse(
                HttpStatus.FOUND,
                "Fetching all Suppliers",
                suppliers
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Creating a supplier")
    @PostMapping(value = "/add-supplier")
    public ResponseEntity<BaseResponse> addNewSupplier(@RequestBody Suppliers suppliers) {

        BaseResponse response = supplierService.addSuppliers(suppliers);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
