package com.journal.florist.backend.feature.order.service;

import com.journal.florist.app.security.SecurityUtils;
import com.journal.florist.app.utils.BaseResponse;
import com.journal.florist.backend.exceptions.AppBaseException;
import com.journal.florist.backend.exceptions.NotFoundException;
import com.journal.florist.backend.feature.customer.service.CustomerService;
import com.journal.florist.backend.feature.order.dto.AddOrderRequest;
import com.journal.florist.backend.feature.order.dto.OrderMapper;
import com.journal.florist.backend.feature.order.dto.UpdateOrderRequest;
import com.journal.florist.backend.feature.order.model.OrderProductDetails;
import com.journal.florist.backend.feature.order.model.OrderShipments;
import com.journal.florist.backend.feature.order.model.Orders;
import com.journal.florist.backend.feature.order.repositories.OrderProductDetailsRepository;
import com.journal.florist.backend.feature.order.repositories.OrderRepository;
import com.journal.florist.backend.feature.order.repositories.OrderShipmentsRepository;
import com.journal.florist.backend.feature.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static com.journal.florist.app.constant.JournalConstants.ORDER_NOT_FOUND_MSG;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderShipmentsRepository shipmentsRepository;
    private final OrderProductDetailsRepository orderProductDetailsRepository;
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
    public BaseResponse addOrder(AddOrderRequest request) {

        var orders = new Orders();
        var shipments = new OrderShipments();
        var details = new OrderProductDetails();

        var authentication = SecurityUtils.getAuthentication();
        String createdBy = authentication.getName();

        var product = productService.findByProductKey(request.getProductId());
        var customer = customerService.getCustomers(request.getCustomerId());

        //Setter order
        orders.setOrderShipments(shipments);
        orders.setOrderProductDetails(details);
        orders.setOrderStatus(request.getOrderStatus());
        orders.setPaymentStatus(request.getPaymentStatus());
        orders.setCustomer(customer);
        orders.setCreatedBy(createdBy);
        orders.setCreatedAt(new Date(System.currentTimeMillis()));

        //Setter order details product.
        details.setProduct(product);
        details.setNotes(request.getNotes());
        details.setQuantity(request.getQuantity());
        details.setAmount(details.getTotalAmount());
        details.setOrder(orders);
        details.setCreatedBy(createdBy);
        details.setCreatedAt(new Date(System.currentTimeMillis()));

        long time;
        var dateTimeDelivery = request.getDateDelivery() + request.getTimeDelivery();
        try {
            var dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            var parse = dateFormat.parse(dateTimeDelivery);
            time = parse.getTime();
        } catch (ParseException e) {
            throw new AppBaseException(String.format("Could not parse date time format %s", dateTimeDelivery));
        }
        shipments.setDeliveryAddress(request.getAddress());
        shipments.setDeliveryDate(new Date(time));
        shipments.setRecipientName(request.getRecipientName());
        shipments.setOrder(orders);
        shipments.setCreatedBy(createdBy);
        shipments.setCreatedAt(new Date(System.currentTimeMillis()));

        orderRepository.save(orders);
        shipmentsRepository.save(shipments);
        orderProductDetailsRepository.save(details);
        historyService.saveHistoryOrder(orders, customer, product);

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
}
