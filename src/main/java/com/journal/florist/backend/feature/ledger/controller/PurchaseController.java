package com.journal.florist.backend.feature.ledger.controller;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.backend.feature.ledger.dto.request.PurchaseRequest;
import com.journal.florist.backend.feature.ledger.model.Purchase;
import com.journal.florist.backend.feature.ledger.service.PurchaseService;
import com.journal.florist.backend.feature.utils.FilterableCrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.journal.florist.app.constant.ApiUrlConstant.PURCHASE_URL;

@Tag(name = "Purchase Endpoint",
        description = "Purchase service api")
@RequiredArgsConstructor
@RestController
@RequestMapping(PURCHASE_URL)
public class PurchaseController {
    private final PurchaseService purchaseService;

    @Operation(summary = "Fetching all purchase in record with pagination")
    @GetMapping
    public ResponseEntity<BaseResponse> getAllPage(
            @RequestParam(defaultValue = "1", required = false) Integer page,
            @RequestParam(defaultValue = "10", required = false) Integer limit) {

        Pageable filter = FilterableCrudService.getPageableWithSort(
                page - 1, limit, Sort.by("createdAt"));
        Page<Purchase> data = purchaseService.getAllSuppliers(filter);

        BaseResponse response = new BaseResponse(HttpStatus.FOUND,
                "Fetching all purchase",
                data);

        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @Operation(summary = "Purchasing product from supplier")
    @PostMapping("/add-purchase")
    public ResponseEntity<?> addNewPurchase(@RequestBody PurchaseRequest request) {
        BaseResponse response = purchaseService.create(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
