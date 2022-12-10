package com.journal.florist.backend.feature.payment.controller;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.backend.feature.payment.dto.PaymentRequest;
import com.journal.florist.backend.feature.payment.dto.PaymentsMapper;
import com.journal.florist.backend.feature.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.journal.florist.app.constant.ApiUrlConstant.PAYMENT_URL;

@Tag(name = "Payment Endpoint",
        description = "Add new Payment for customer order")
@RequiredArgsConstructor
@RestController
@RequestMapping(PAYMENT_URL)
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "Add payment",
            description = "Payment customer by order id")
    @PostMapping
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_CASHIER')")
    public ResponseEntity<BaseResponse> addPaymentOrder(@Valid @RequestBody PaymentRequest request) {
        PaymentsMapper data = paymentService.creditPayment(request);

        BaseResponse response = new BaseResponse(HttpStatus.CREATED,
                String.format("Payment in order %s is successfully", request.orderId()),
                data);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
