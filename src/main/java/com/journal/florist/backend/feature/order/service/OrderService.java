package com.journal.florist.backend.feature.order.service;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.app.common.utils.HasLogger;
import com.journal.florist.backend.feature.order.dto.AddOrderRequest;
import com.journal.florist.backend.feature.order.dto.OrderMapper;
import com.journal.florist.backend.feature.order.dto.UpdateOrderRequest;
import com.journal.florist.backend.feature.order.model.Orders;

public interface OrderService extends HasLogger {

    OrderMapper getOrderById(String orderId);

    Orders findOrderById(String orderId);

    BaseResponse addOrder(AddOrderRequest request);
    BaseResponse updateOrder(UpdateOrderRequest request);

}
