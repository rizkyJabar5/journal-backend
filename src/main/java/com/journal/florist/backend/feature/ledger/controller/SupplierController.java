package com.journal.florist.backend.feature.ledger.controller;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.backend.feature.ledger.dto.SuppliersMapper;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.journal.florist.app.constant.ApiUrlConstant.SUPPLIER_URL;

@Tag(name = "Supplier Endpoint",
        description = "Endpoint for all service customer")
@RequiredArgsConstructor
@RestController
@RequestMapping(SUPPLIER_URL)
public class SupplierController {

    private final SupplierService supplierService;
    private final SuppliersMapper suppliersMapper;

    @Operation(summary = "Fetching all supplier found in record")
    @GetMapping
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<BaseResponse> getAllSupplier(
            @RequestParam(name = "page",
                    required = false,
                    defaultValue = "1") int page,
            @RequestParam(name = "limit",
                    required = false,
                    defaultValue = "10") int limit) {
        Pageable filter = FilterableCrudService.getPageable(page - 1, limit);
        Page<SuppliersMapper> suppliers = supplierService.getAllSuppliers(filter)
                .map(suppliersMapper::buildSuppliersResponse);

        if (suppliers.isEmpty()) {
            BaseResponse response = new BaseResponse(
                    HttpStatus.OK,
                    "Record not found in suppliers",
                    null
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        BaseResponse response = new BaseResponse(
                HttpStatus.OK,
                "Fetching all Suppliers",
                suppliers
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Fetching all supplier with debt in record")
    @GetMapping("/supplier-no-debt")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<BaseResponse> getAllSupplierWithNoDebt(
            @RequestParam(name = "page",
                    required = false,
                    defaultValue = "1") int page,
            @RequestParam(name = "limit",
                    required = false,
                    defaultValue = "10") int limit) {
        Pageable filter = FilterableCrudService.getPageable(page - 1, limit);
        Page<SuppliersMapper> suppliers = supplierService.getAllSuppliersWithNoDebt(filter)
                .map(suppliersMapper::buildSuppliersResponse);

        if (suppliers.isEmpty()) {
            BaseResponse response = new BaseResponse(
                    HttpStatus.OK,
                    "Record not found in suppliers",
                    null
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        BaseResponse response = new BaseResponse(
                HttpStatus.OK,
                "Fetching all Suppliers",
                suppliers
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Fetching all supplier with debt in record")
    @GetMapping("/supplier-debt")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<BaseResponse> getAllSupplierWithDebt(
            @RequestParam(name = "page",
                    required = false,
                    defaultValue = "1") int page,
            @RequestParam(name = "limit",
                    required = false,
                    defaultValue = "10") int limit) {
        Pageable filter = FilterableCrudService.getPageable(page - 1, limit);
        Page<SuppliersMapper> suppliers = supplierService.getAllSuppliersWithDebt(filter)
                .map(suppliersMapper::buildSuppliersResponse);

        if (suppliers.isEmpty()) {
            BaseResponse response = new BaseResponse(
                    HttpStatus.OK,
                    "Record not found in suppliers",
                    null
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        BaseResponse response = new BaseResponse(
                HttpStatus.OK,
                "Fetching all Suppliers",
                suppliers
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get supplier by Id")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<BaseResponse> getSupplierById(
            @PathVariable("id") String supplierId) {
        Suppliers supplier = supplierService.getSupplierById(supplierId);
        SuppliersMapper mapper = suppliersMapper.buildSuppliersResponse(supplier);

        BaseResponse response = new BaseResponse(
                HttpStatus.OK,
                "Fetching all Suppliers",
                mapper
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Creating a supplier")
    @PostMapping(value = "/add-supplier")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<BaseResponse> addNewSupplier(@RequestBody Suppliers suppliers) {

        BaseResponse response = supplierService.addSuppliers(suppliers);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }
}
