package com.journal.florist.backend.feature.order.service.impl;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.app.security.SecurityUtils;
import com.journal.florist.backend.exceptions.AppBaseException;
import com.journal.florist.backend.exceptions.NotFoundException;
import com.journal.florist.backend.feature.customer.service.CustomerService;
import com.journal.florist.backend.feature.ledger.repositories.SalesRepositories;
import com.journal.florist.backend.feature.ledger.service.SalesService;
import com.journal.florist.backend.feature.order.dto.AddOrderRequest;
import com.journal.florist.backend.feature.order.dto.OrderMapper;
import com.journal.florist.backend.feature.order.dto.UpdateOrderRequest;
import com.journal.florist.backend.feature.order.enums.OrderStatus;
import com.journal.florist.backend.feature.order.enums.PaymentStatus;
import com.journal.florist.backend.feature.order.model.OrderDetails;
import com.journal.florist.backend.feature.order.model.OrderShipments;
import com.journal.florist.backend.feature.order.model.Orders;
import com.journal.florist.backend.feature.order.repositories.OrderRepository;
import com.journal.florist.backend.feature.order.service.OrderDetailService;
import com.journal.florist.backend.feature.order.service.OrderService;
import com.journal.florist.backend.feature.order.service.ShipmentService;
import com.journal.florist.backend.feature.product.service.ProductService;
import com.journal.florist.backend.feature.utils.EntityUtil;
import com.journal.florist.backend.feature.utils.FilterableCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.journal.florist.app.constant.JournalConstants.NOT_FOUND_MSG;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final SalesRepositories salesRepository;

    private final OrderRepository orderRepository;
    private final ShipmentService shipmentService;
    private final OrderDetailService orderDetailService;
    private final SalesService salesService;
    private final ProductService productService;
    private final CustomerService customerService;
    private final OrderMapper orderMapper;

    @Override
    public OrderMapper getOrderById(String orderId) {
        return Optional.ofNullable(orderRepository.findOrderByPublicKey(orderId))
                .map(orderMapper::buildOrderResponse)
                .orElseThrow(() -> new NotFoundException(
                        String.format(NOT_FOUND_MSG, EntityUtil.getName(Orders.class), orderId)));
    }

    @Override
    public Page<OrderMapper> getByField(String orderDate,
                                        OrderStatus orderStatus,
                                        PaymentStatus paymentStatus,
                                        Pageable pageable) {

        return null;
    }

    @Override
    public Page<Orders> getOrderPaidOff(int page, int limit) {
        return orderRepository.findOrderPaidOff(FilterableCrudService.getPageable(page, limit));
    }

    @Override
    public List<Orders> getOrderByCustomerName(String customerName) {
        return orderRepository.findOrderByCustomerName(customerName);
    }

    @Override
    public List<Orders> findOrderByStatus(OrderStatus orderStatus) {
        return null;
    }

    @Override
    public List<Orders> findOrderByDate(String orderDate) {
        return null;
    }

    @Override
    public List<Orders> findOrderPaymentStatus(PaymentStatus paymentStatus) {
        return null;
    }

    @Override
    public Orders findOrderById(String orderId) {
        return Optional.ofNullable(orderRepository.findOrderByPublicKey(orderId))
                .orElseThrow(() -> new NotFoundException(
                        String.format(NOT_FOUND_MSG, EntityUtil.getName(Orders.class), orderId)));
    }


    @Override
    @Transactional
    public BaseResponse addOrder(AddOrderRequest request) {

        var orders = new Orders();
        var customer = customerService.getCustomers(request.getCustomerId());
        var authentication = SecurityUtils.getAuthentication();
        String createdBy = authentication.getName();

        //Setter order
        orders = create(
                new Orders(
                        customer,
                        request.getPaymentStatus(),
                        request.getOrderStatus(),
                        createdBy,
                        new Date(System.currentTimeMillis())
                )
        );

        //Setter order details product.
        Set<OrderDetails> orderDetails = new HashSet<>();
        for (AddOrderRequest.OrderDetailDto detail : request.getDetailProduct()) {
            var product = productService.findByProductKey(detail.getProductId());
            orderDetails.add(orderDetailService.create(
                            new OrderDetails(orders,
                                    product,
                                    detail.getNotes(),
                                    detail.getQuantity(),
                                    product.getCostPrice(),
                                    product.getPrice())
                    )
            );
        }
        orders.setOrderDetails(orderDetails);

        // Setter order shipment
        Date deliveryDate = null;
        if (Objects.nonNull(request.getDateDelivery()) && Objects.nonNull(request.getTimeDelivery())) {
            var dateTimeDelivery = request.getDateDelivery() + request.getTimeDelivery();
            try {
                var dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                var parse = dateFormat.parse(dateTimeDelivery);
                long time = parse.getTime();
                if (time != 0) {
                    deliveryDate = new Date(time);
                }
            } catch (ParseException e) {
                throw new AppBaseException(String.format("Could not parse date time format %s", dateTimeDelivery));
            }
        }

        OrderShipments shipment = shipmentService.create(new OrderShipments(
                orders,
                request.getRecipientName(),
                request.getAddress(),
                deliveryDate
        ));
        orders.setOrderShipment(shipment);

        update(orders);

        salesService.saveSales(orders, customer);

        getLogger().info("Successfully to save new order");
        OrderMapper mapper = orderMapper.buildOrderResponse(orders);
        return new BaseResponse(
                HttpStatus.CREATED,
                "Customer is successfully order product",
                mapper);
    }

    @Override
    public BaseResponse updateOrder(UpdateOrderRequest request) {
        return null;
    }

    private Orders create(Orders orders) {
        return orderRepository.save(orders);
    }

    private void update(Orders orders) {
        orderRepository.save(orders);
    }
}
