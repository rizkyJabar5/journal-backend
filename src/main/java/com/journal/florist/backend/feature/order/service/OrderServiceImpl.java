package com.journal.florist.backend.feature.order.service;

import com.journal.florist.app.security.SecurityUtils;
import com.journal.florist.app.utils.BaseResponse;
import com.journal.florist.backend.exceptions.AppBaseException;
import com.journal.florist.backend.exceptions.NotFoundException;
import com.journal.florist.backend.feature.customer.service.CustomerService;
import com.journal.florist.backend.feature.order.dto.AddOrderRequest;
import com.journal.florist.backend.feature.order.dto.OrderMapper;
import com.journal.florist.backend.feature.order.dto.UpdateOrderRequest;
import com.journal.florist.backend.feature.order.model.OrderDetails;
import com.journal.florist.backend.feature.order.model.OrderShipments;
import com.journal.florist.backend.feature.order.model.Orders;
import com.journal.florist.backend.feature.order.repositories.OrderRepository;
import com.journal.florist.backend.feature.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.journal.florist.app.constant.JournalConstants.ORDER_NOT_FOUND_MSG;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ShipmentService shipmentService;
    private final OrderDetailService orderDetailService;
    private final HistoryOrderService historyService;
    private final ProductService productService;
    private final CustomerService customerService;
    private final OrderMapper orderMapper;

    @Override
    public OrderMapper getOrderById(String orderId) {
        return Optional.ofNullable(orderRepository.findOrderByPublicKey(orderId))
                .map(orderMapper::mapToEntity)
                .orElseThrow(() -> new NotFoundException(String.format(ORDER_NOT_FOUND_MSG, orderId)));
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
                                    product.getPrice())
                    )
            );
            historyService.saveHistoryOrder(orders, customer, product);
        }
        orders.setOrderDetails(orderDetails);

        // Setter order shipment
        long time;
        var dateTimeDelivery = request.getDateDelivery() + request.getTimeDelivery();
        try {
            var dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            var parse = dateFormat.parse(dateTimeDelivery);
            time = parse.getTime();
        } catch (ParseException e) {
            throw new AppBaseException(String.format("Could not parse date time format %s", dateTimeDelivery));
        }

        OrderShipments shipment = shipmentService.create(new OrderShipments(
                orders,
                request.getRecipientName(),
                request.getAddress(),
                new Date(time)
        ));
        orders.setOrderShipment(shipment);

        update(orders);

        getLogger().info("Successfully to save new order");
        OrderMapper mapper = orderMapper.mapToEntity(orders);
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
