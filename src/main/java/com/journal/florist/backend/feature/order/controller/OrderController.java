package com.journal.florist.backend.feature.order.controller;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.backend.feature.order.dto.AddOrderRequest;
import com.journal.florist.backend.feature.order.dto.OrderMapper;
import com.journal.florist.backend.feature.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.journal.florist.app.constant.ApiUrlConstant.ORDER_URL;

@RestController
@RequestMapping(ORDER_URL)
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PostMapping("/add-order")
    public ResponseEntity<BaseResponse> addOrder(@Valid @RequestBody AddOrderRequest request) {
        var response = orderService.addOrder(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/order-done")
    public ResponseEntity<BaseResponse> getOrderPaidOff(@RequestParam(name = "page") int page,
                                                        @RequestParam(name = "limit") int limit) {
        Page<OrderMapper> orderPaidOff = orderService.getOrderPaidOff(page, limit)
                .map(orderMapper::buildOrderResponse);

        if (orderPaidOff.isEmpty()) {
            BaseResponse response = new BaseResponse(
                    HttpStatus.NOT_FOUND,
                    "No content we found",
                    null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        BaseResponse response = new BaseResponse(
                HttpStatus.OK,
                "fetching orders with paid status",
                orderPaidOff);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
