package com.journal.florist.backend.feature.order.service.impl;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.app.security.SecurityUtils;
import com.journal.florist.backend.exceptions.AppBaseException;
import com.journal.florist.backend.exceptions.NotFoundException;
import com.journal.florist.backend.feature.customer.service.CustomerDebtService;
import com.journal.florist.backend.feature.customer.service.CustomerService;
import com.journal.florist.backend.feature.ledger.service.FinanceService;
import com.journal.florist.backend.feature.ledger.service.SalesService;
import com.journal.florist.backend.feature.order.dto.AddOrderRequest;
import com.journal.florist.backend.feature.order.dto.OrdersMapper;
import com.journal.florist.backend.feature.order.dto.UpdateOrderRequest;
import com.journal.florist.backend.feature.order.enums.OrderStatus;
import com.journal.florist.backend.feature.order.enums.PaymentStatus;
import com.journal.florist.backend.feature.order.model.OrderDetails;
import com.journal.florist.backend.feature.order.model.OrderShipments;
import com.journal.florist.backend.feature.order.model.Orders;
import com.journal.florist.backend.feature.payment.model.Payments;
import com.journal.florist.backend.feature.order.repositories.OrderRepository;
import com.journal.florist.backend.feature.order.service.OrderDetailService;
import com.journal.florist.backend.feature.order.service.OrderService;
import com.journal.florist.backend.feature.payment.service.PaymentLogService;
import com.journal.florist.backend.feature.payment.service.PaymentService;
import com.journal.florist.backend.feature.order.service.ShipmentService;
import com.journal.florist.backend.feature.product.service.ProductService;
import com.journal.florist.backend.feature.utils.EntityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.journal.florist.app.constant.JournalConstants.NOT_FOUND_MSG;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ShipmentService shipmentService;
    private final OrderDetailService orderDetailService;
    private final SalesService salesService;
    private final ProductService productService;
    private final CustomerService customerService;
    private final OrdersMapper orderMapper;
    private final PaymentService paymentService;
    private final PaymentLogService paymentLogService;
    private final CustomerDebtService customerDebtService;
    private final FinanceService financeService;

    @Override
    public OrdersMapper getOrderById(String orderId) {
        return Optional.ofNullable(orderRepository.findOrderByPublicKey(orderId))
                .map(orderMapper::buildOrderResponse)
                .orElseThrow(() -> new NotFoundException(
                        String.format(NOT_FOUND_MSG, EntityUtil.getName(Orders.class), orderId)));
    }

    @Override
    public Page<OrdersMapper> getByField(String orderDate,
                                         OrderStatus orderStatus,
                                         PaymentStatus paymentStatus,
                                         Pageable pageable) {

        return null;
    }

    @Override
    public Page<OrdersMapper> getAllOrder(Pageable pageable) {
        return orderRepository.findAllOrder(pageable)
                .map(orderMapper::buildOrderResponse);
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
    public BaseResponse addOrder(AddOrderRequest request) {

        var orders = new Orders();
        var customer = customerService.getCustomerId(request.getCustomerId());
        var authentication = SecurityUtils.getAuthentication();
        String createdBy = authentication.getName();

        //Setter order
        orders = orderRepository.saveAndFlush(
                new Orders(
                        customer,
                        request.getOrderStatus(),
                        createdBy,
                        new Date(System.currentTimeMillis()))
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
                            product.getPrice()))
            );
        }
        orders.setOrderDetails(orderDetails);

        // Setter order shipment
        Date deliveryDate = parseDateToEpoch(request.getDateDelivery(), request.getTimeDelivery());
        OrderShipments shipment = shipmentService.create(new OrderShipments(
                orders,
                request.getRecipientName(),
                request.getAddress(),
                deliveryDate
        ));
        orders.setOrderShipment(shipment);

        // Customer payment check
        customerCheckPayment(request.getPaymentAmount(), orders.getTotalOrderAmount(), orders);
        create(orders);

        salesService.saveSales(orders, customer);
        financeService.addAccountReceivableAndRevenue(
                customerDebtService.sumAllTotalCustomerDebt(),
                paymentLogService.sumTotalAmountPayment());

        getLogger().info("Successfully to save new order");
        OrdersMapper mapper = orderMapper.buildOrderResponse(orders);
        return new BaseResponse(
                HttpStatus.CREATED,
                "Customer is successfully order product",
                mapper);
    }

    @Override
    public BaseResponse updateOrder(UpdateOrderRequest request) {
        request.checkOrderId();
        Orders persisted = findOrderById(request.orderId());
        var updatedBy = SecurityUtils.getAuthentication().getName();

        persisted.setOrderStatus(request.orderStatus());

        if (request.recipientName() != null
                || request.address() != null
                || request.dateDelivery() != null
                || request.timeDelivery() != null) {
            Date deliveryDate = parseDateToEpoch(request.dateDelivery(), request.timeDelivery());

            OrderShipments shipment = shipmentService.findShipmentByOrderId(persisted.getPublicKey());
            shipment.setRecipientName(request.recipientName());
            shipment.setDeliveryAddress(request.address());
            shipment.setDeliveryDate(deliveryDate);
            shipmentService.create(shipment);
        }
        persisted.setLastModifiedBy(updatedBy);
        persisted.setLastModifiedDate(new Date(System.currentTimeMillis()));

        Orders newUpdateOrder = create(persisted);

        OrdersMapper mapper = orderMapper.buildOrderResponse(newUpdateOrder);

        return new BaseResponse(
                HttpStatus.CREATED,
                String.format("Order with id %s successfully to update", request.orderId()),
                mapper);
    }

    // Customer payment check
    private void customerCheckPayment(BigDecimal paymentAmount,
                                      BigDecimal totalToBePaid,
                                      Orders orders) {
        if (paymentAmount != null) {
            BigDecimal result = totalToBePaid.subtract(paymentAmount);

            if (result.compareTo(BigDecimal.ZERO) > 0) {
                customerDebtService.addDebtCustomer(orders.getCustomer(), result);
                orders.setPaymentStatus(PaymentStatus.DOWN_PAYMENT);
            } else if (result.compareTo(BigDecimal.ZERO) == 0) {
                orders.setPaymentStatus(PaymentStatus.PAID_OFF);
            } else {
                BigDecimal paymentOver = paymentAmount.subtract(totalToBePaid);
                throw new AppBaseException(String.format("Customer payment is over +%s", paymentOver));
            }

            Payments payments = paymentService.addPayment(paymentAmount, result, orders);
            orders.setPayment(payments);
            return;
        }
        /*
         * If the paymentAmount is null, will be created customer payment bill.
         * And add the debt of customer.
         *
         * And then order status will have Payment status value NOT_YET_PAID
         */
        Payments payments = paymentService.addPayment(BigDecimal.ZERO, totalToBePaid, orders);
        customerDebtService.addDebtCustomer(orders.getCustomer(), orders.getTotalOrderAmount());
        orders.setPayment(payments);
    }

    private Date parseDateToEpoch(String dateDelivery, String timeDelivery) {
        Date deliveryDate = null;
        if (Objects.nonNull(dateDelivery) && Objects.nonNull(timeDelivery)) {
            var dateTimeDelivery = dateDelivery + timeDelivery;
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

        return deliveryDate;
    }

    private Orders create(Orders orders) {
        return orderRepository.save(orders);
    }
}
