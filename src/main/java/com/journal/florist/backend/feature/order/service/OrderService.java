package com.journal.florist.backend.feature.order.service;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.app.common.utils.HasLogger;
import com.journal.florist.backend.feature.order.dto.AddOrderRequest;
import com.journal.florist.backend.feature.order.dto.OrdersMapper;
import com.journal.florist.backend.feature.order.dto.UpdateOrderRequest;
import com.journal.florist.backend.feature.order.enums.OrderStatus;
import com.journal.florist.backend.feature.order.enums.PaymentStatus;
import com.journal.florist.backend.feature.order.model.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService extends HasLogger {

    OrdersMapper getOrderById(String orderId);
    Page<OrdersMapper> getByField(String orderDate,
                                  OrderStatus orderStatus,
                                  PaymentStatus paymentStatus,
                                  Pageable pageable);
    Page<Orders> getOrderPaidOff(int page, int limit);
    List<Orders> getOrderByCustomerName(String customerName);
    List<Orders> findOrderByStatus(OrderStatus orderStatus);
    List<Orders> findOrderByDate(String orderDate);
    List<Orders> findOrderPaymentStatus(PaymentStatus paymentStatus);
    Orders findOrderById(String orderId);

    BaseResponse addOrder(AddOrderRequest request);
    BaseResponse updateOrder(UpdateOrderRequest request);

}
