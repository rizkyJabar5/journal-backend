package com.journal.florist.backend.feature.ledger.controller;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.backend.feature.ledger.service.FinanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.journal.florist.app.constant.ApiUrlConstant.FINANCE_URL;

@Tag(name = "Finance Endpoint", description = "API summary finance")
@RequiredArgsConstructor
@RestController
@RequestMapping(FINANCE_URL)
public class FinanceController {

    private final FinanceService financeService;

    @Operation(summary = "Get finance by date")
    @GetMapping
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<BaseResponse> getFinanceByDate(
            @RequestParam(required = false)
            @Schema(example = "23-12-2000")
            String date) {

        BaseResponse response = financeService.getFinanceByDate(date);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
