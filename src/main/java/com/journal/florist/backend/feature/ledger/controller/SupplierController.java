package com.journal.florist.backend.feature.ledger.controller;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.backend.feature.ledger.model.Suppliers;
import com.journal.florist.backend.feature.ledger.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.journal.florist.app.constant.ApiUrlConstant.SUPPLIER_URL;

@RequiredArgsConstructor
@RestController
@RequestMapping(SUPPLIER_URL)
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping
    public ResponseEntity<?> getAllSupplier() {
        List<Suppliers> suppliers = supplierService.getAllSuppliers();
        if(suppliers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(suppliers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BaseResponse> addNewSupplier(@RequestBody Suppliers suppliers) {

        Suppliers data = supplierService.addSuppliers(suppliers);

        BaseResponse response = new BaseResponse(
                HttpStatus.OK,
                "Success to persist new Supplier",
                data);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
