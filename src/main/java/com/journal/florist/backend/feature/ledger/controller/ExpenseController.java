package com.journal.florist.backend.feature.ledger.controller;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.backend.feature.ledger.dto.request.ExpenseRequest;
import com.journal.florist.backend.feature.ledger.model.Expense;
import com.journal.florist.backend.feature.ledger.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.journal.florist.app.constant.ApiUrlConstant.EXPENSE_URL;

@RequiredArgsConstructor
@RestController
@RequestMapping(EXPENSE_URL)
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<BaseResponse> addNewExpense(@RequestBody ExpenseRequest request) {

        Expense data = expenseService.create(request);
        BaseResponse response = new BaseResponse(
                HttpStatus.OK,
                "Successfully to persist new Expense",
                data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
