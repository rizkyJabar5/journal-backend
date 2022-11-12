package com.journal.florist.backend.feature.ledger.controller;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.backend.feature.ledger.model.Suppliers;
import com.journal.florist.backend.feature.ledger.service.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> getAllSupplier() {
        List<Suppliers> suppliers = supplierService.getAllSuppliers();
        if (suppliers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(suppliers, HttpStatus.OK);
    }

    @Operation(summary = "Creating a supplier")
    @PostMapping(value = "/add-supplier")
    public ResponseEntity<BaseResponse> addNewSupplier(@RequestBody Suppliers suppliers) {

        BaseResponse response = supplierService.addSuppliers(suppliers);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
