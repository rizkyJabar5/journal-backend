package com.journal.florist.backend.feature.order.controller;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.backend.feature.order.dto.AddOrderRequest;
import com.journal.florist.backend.feature.order.service.DeliveryNoteService;
import com.journal.florist.backend.feature.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.journal.florist.app.constant.ApiUrlConstant.ORDER_URL;

@RestController
@RequestMapping(ORDER_URL)
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final DeliveryNoteService deliveryNoteService;

    @PostMapping("/add-order")
    public ResponseEntity<BaseResponse> addOrder(@Valid @RequestBody AddOrderRequest request) {
        var response = orderService.addOrder(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/printed-delivery-note/{orderId}")
    public ResponseEntity<?> printDeliveryNote(@PathVariable String orderId,
                                               String gnrId,
                                               HttpServletResponse httpResponse) {

        ContentDisposition content = ContentDisposition
                .builder("inline")
                .filename("Delivery Note " + orderId + ".pdf")
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(content);

        BaseResponse response = deliveryNoteService.create(orderId, gnrId);

        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }
}
