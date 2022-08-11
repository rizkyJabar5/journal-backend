package com.journal.florist.backend.feature.order.service;

import com.journal.florist.app.utils.BaseResponse;
import com.journal.florist.app.utils.HasLogger;
import com.journal.florist.backend.feature.order.dto.AddOrderRequest;
import com.journal.florist.backend.feature.order.dto.OrderMapper;
import com.journal.florist.backend.feature.order.dto.UpdateOrderRequest;

public interface OrderService extends HasLogger {

    OrderMapper getOrderById(String orderId);
    BaseResponse addOrder(AddOrderRequest request);
    BaseResponse updateOrder(UpdateOrderRequest request);

}
