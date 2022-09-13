package com.journal.florist.backend.feature.ledger.controller;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.backend.feature.ledger.model.Suppliers;
import com.journal.florist.backend.feature.ledger.service.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    @GetMapping(value = "")
    public ResponseEntity<?> getAllSupplier() {
        List<Suppliers> suppliers = supplierService.getAllSuppliers();
        if (suppliers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(suppliers, HttpStatus.OK);
    }

    @Operation(summary = "Creating a supplier")
    @PostMapping(value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> addNewSupplier(@RequestBody Suppliers suppliers) {

        Suppliers data = supplierService.addSuppliers(suppliers);

        BaseResponse response = new BaseResponse(
                HttpStatus.OK,
                "Success to persist new Supplier",
                data);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
