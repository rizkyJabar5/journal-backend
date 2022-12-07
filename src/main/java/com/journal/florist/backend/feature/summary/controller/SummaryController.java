package com.journal.florist.backend.feature.summary.controller;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.backend.feature.summary.service.DashboardSummaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.journal.florist.app.constant.ApiUrlConstant.SUMMARY_URL;

@Tag(name = "Summary Endpoint")
@RestController
@RequiredArgsConstructor
@RequestMapping(SUMMARY_URL)
public class SummaryController {

    private final DashboardSummaryService summaryService;

    @Operation(summary = "Fetching summary ledger")
    @GetMapping("/ledger")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<BaseResponse> getSummaryLedger() {

        BaseResponse response = summaryService.summaryLedger();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Fetching summary ledger")
    @GetMapping("/store")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<BaseResponse> getSummaryStore() {

        BaseResponse response = summaryService.summaryStore();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
