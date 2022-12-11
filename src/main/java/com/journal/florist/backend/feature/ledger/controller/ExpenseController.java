package com.journal.florist.backend.feature.ledger.controller;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.backend.feature.ledger.dto.request.ExpenseRequest;
import com.journal.florist.backend.feature.ledger.model.Expense;
import com.journal.florist.backend.feature.ledger.service.ExpenseService;
import com.journal.florist.backend.feature.utils.FilterableCrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.journal.florist.app.constant.ApiUrlConstant.EXPENSE_URL;

@Tag(name = "Expense Endpoint",
        description = "Api for expenditure service")
@RequiredArgsConstructor
@RestController
@RequestMapping(EXPENSE_URL)
public class ExpenseController {

    private final ExpenseService expenseService;

    @Operation(summary = "Fetching all expense in record with pagination")
    @GetMapping
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<BaseResponse> getAllExpensePage(
            @RequestParam(defaultValue = "1", required = false) Integer page,
            @RequestParam(defaultValue = "10", required = false) Integer limit) {

        Pageable filter = FilterableCrudService.getPageableWithSort(
                page - 1, limit, Sort.by("createdAt"));
        Page<Expense> data = expenseService.getAllExpense(filter);

        if (data.isEmpty()) {
            BaseResponse response = new BaseResponse(
                    HttpStatus.OK,
                    "Record not found in expense",
                    null
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        BaseResponse response = new BaseResponse(HttpStatus.OK,
                "Fetching all expense",
                data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Fetching expense by id")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<BaseResponse> getExpenseById(@PathVariable("id") String expenseId) {
        Expense data = expenseService.findExpenseById(expenseId);

        BaseResponse response = new BaseResponse(
                HttpStatus.OK,
                String.format("Expense with id found %s", expenseId),
                data
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Add new expense and will be request send to server")
    @PostMapping(value = "/add-expense", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<BaseResponse> addNewExpense(@ModelAttribute ExpenseRequest request) {

        Expense data = expenseService.create(request);
        BaseResponse response = new BaseResponse(
                HttpStatus.CREATED,
                "Successfully to persist new Expense",
                data);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
