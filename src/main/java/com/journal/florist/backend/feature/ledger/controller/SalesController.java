package com.journal.florist.backend.feature.ledger.controller;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.backend.feature.ledger.dto.SalesMapper;
import com.journal.florist.backend.feature.ledger.service.SalesService;
import com.journal.florist.backend.feature.utils.FilterableCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.journal.florist.app.constant.ApiUrlConstant.REPORT_SALES;

@RequiredArgsConstructor
@RestController
@RequestMapping(REPORT_SALES)
public class SalesController {

    private final SalesService salesService;

    @GetMapping
    public ResponseEntity<BaseResponse> getAllReport(@RequestParam(required = false) int page,
                                                     @RequestParam(required = false) int limit) {
        Pageable filter = FilterableCrudService.getPageableWithSort(
                page - 1, limit, Sort.by("saleDate").descending());
        Page<SalesMapper> report = salesService.getAllSalesReport(filter);
        BaseResponse response = new BaseResponse(HttpStatus.OK, "Fetching report sales", report);

        if (report.isEmpty()) {
            BaseResponse responseNotFound = new BaseResponse(
                    HttpStatus.NOT_FOUND, "Record not found", null);

            return new ResponseEntity<>(responseNotFound, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
