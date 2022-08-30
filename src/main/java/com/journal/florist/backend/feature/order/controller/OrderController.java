package com.journal.florist.backend.feature.order.controller;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.backend.feature.order.dto.AddOrderRequest;
import com.journal.florist.backend.feature.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.journal.florist.app.constant.ApiUrlConstant.ORDER_URL;

@RestController
@RequestMapping(ORDER_URL)
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/add-order")
    public ResponseEntity<BaseResponse> addOrder(@Valid @RequestBody AddOrderRequest request) {
        var response = orderService.addOrder(request);
        return ResponseEntity.ok().body(response);
    }

}
