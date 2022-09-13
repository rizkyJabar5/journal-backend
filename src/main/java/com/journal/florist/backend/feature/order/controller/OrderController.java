package com.journal.florist.backend.feature.order.controller;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.backend.feature.order.dto.AddOrderRequest;
import com.journal.florist.backend.feature.order.dto.OrdersMapper;
import com.journal.florist.backend.feature.order.dto.UpdateOrderRequest;
import com.journal.florist.backend.feature.order.service.OrderService;
import com.journal.florist.backend.feature.utils.FilterableCrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.journal.florist.app.constant.ApiUrlConstant.ORDER_URL;

@Tag(name = "Order endpoint",
        description = "Transaction for new order or fetching existing order")
@RestController
@RequestMapping(ORDER_URL)
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Fetching all order in record with pagination")
    @GetMapping(value = "")
    public ResponseEntity<BaseResponse> getAllOrder(
            @RequestParam(name = "page",
                    required = false,
                    defaultValue = "1") int page,
            @RequestParam(name = "limit",
                    required = false,
                    defaultValue = "10") int limit) {
        Pageable filter = FilterableCrudService.getPageable(page - 1, limit);
        Page<OrdersMapper> orderPaidOff = orderService.getAllOrder(filter);

        BaseResponse response = new BaseResponse(
                HttpStatus.OK,
                "fetching orders with paid status",
                orderPaidOff);

        if (orderPaidOff.isEmpty()) {
            BaseResponse noContent = new BaseResponse(
                    HttpStatus.NOT_FOUND,
                    "No content we found",
                    null);
            return new ResponseEntity<>(noContent, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Creating new order")
    @PostMapping(value = "/add-order",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> addNewOrder(@Valid @RequestBody AddOrderRequest request) {
        var response = orderService.addOrder(request);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Update existing order")
    @PutMapping(value = "/update-order",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> updateExistingOrder(@Valid @RequestBody UpdateOrderRequest request) {
        var response = orderService.updateOrder(request);
        return ResponseEntity.ok().body(response);
    }
}
