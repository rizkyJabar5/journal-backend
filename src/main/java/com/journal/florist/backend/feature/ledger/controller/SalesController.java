package com.journal.florist.backend.feature.ledger.controller;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.backend.feature.ledger.dto.SalesMapper;
import com.journal.florist.backend.feature.ledger.service.SalesService;
import com.journal.florist.backend.feature.utils.FilterableCrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.journal.florist.app.constant.ApiUrlConstant.REPORT_SALES;

@Tag(name = "Sales Endpoint",
        description = "Record sales for every add new order")
@RequiredArgsConstructor
@RestController
@RequestMapping(REPORT_SALES)
public class SalesController {

    private final SalesService salesService;

    @Operation(summary = "Fetching all sales with pagination and sort sale date to descending")
    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<BaseResponse> getAllReport(
            @RequestParam(defaultValue = "1", required = false) Integer page,
            @RequestParam(defaultValue = "10", required = false) Integer limit) {

        Pageable filter = FilterableCrudService.getPageableWithSort(
                page - 1, limit, Sort.by("saleDate").descending());
        Page<SalesMapper> report = salesService.getAllSalesReport(filter);

        if (report.isEmpty()) {
            BaseResponse responseNotFound = new BaseResponse(
                    HttpStatus.NO_CONTENT,
                    "Record not found",
                    null);

            return new ResponseEntity<>(responseNotFound, HttpStatus.OK);
        }

        BaseResponse response = new BaseResponse(
                HttpStatus.OK,
                "Fetching report sales",
                report);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
