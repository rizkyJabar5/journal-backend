package com.journal.florist.backend.feature.order.dto;

import com.journal.florist.app.utils.DateConverter;
import com.journal.florist.backend.feature.order.model.Orders;
import com.journal.florist.backend.feature.utils.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class OrderMapper implements Serializable {
    private String orderId;
    private String customerName;
    private String phoneNumber;
    private String productId;
    private String productName;
    private String notes;
    private Integer quantity;
    private BigInteger totalAmount;
    private String paymentStatus;
    private String orderStatus;
    private String recipientName;
    private String deliveryAddress;
    private String deliveryDate;
    private String deliveryTime;
    private String addedBy;
    private String createdAt;
    private String updatedBy;
    private String updatedAt;

    public OrderMapper mapToEntity(Orders orders) {

        LocalDateTime dateTime = DateConverter.toLocalDateTime(orders.getCreatedAt());
        String addedDate = DateConverter.formatDateTime().format(dateTime);
        String modifiedDate = null;

        String modifiedBy = orders.getLastModifiedBy();

        if(orders.getLastModifiedDate() != null) {
            LocalDateTime toLocalDate = DateConverter.toLocalDateTime(orders.getLastModifiedDate());
            modifiedDate = DateConverter.formatDateTime().format(toLocalDate);
        }

        if(modifiedBy == null) {
            updatedBy = null;
        }

        var date = DateConverter.toLocalDate(orders.getOrderShipments().getDeliveryDate());
        var dateFormat = DateConverter.formatDate().format(date);
        var timeFormat = DateConverter.formatTime().format(date);

        return OrderMapper.builder()
                .orderId(orders.getPublicKey())
                .customerName(orders.getCustomer().getName())
                .phoneNumber(orders.getCustomer().getPhoneNumber())
                .productId(orders.getOrderProductDetails().getProduct().getPublicKey())
                .productName(orders.getOrderProductDetails().getProduct().getProductName())
                .notes(orders.getOrderProductDetails().getNotes())
                .quantity(orders.getOrderProductDetails().getQuantity())
                .totalAmount(orders.getOrderProductDetails().getTotalAmount())
                .paymentStatus(orders.getPaymentStatus().name())
                .orderStatus(orders.getOrderStatus().name())
                .recipientName(orders.getOrderShipments().getRecipientName())
                .deliveryAddress(getFullDeliveryAddress(orders.getOrderShipments().getDeliveryAddress()))
                .deliveryDate(dateFormat)
                .deliveryTime(timeFormat)
                .addedBy(orders.getCreatedBy())
                .createdAt(addedDate)
                .updatedBy(modifiedBy)
                .updatedAt(modifiedDate)
                .build();
    }

    private String getFullDeliveryAddress(Address address) {

        return address.getStreet() + ", "
                + address.getCity() + ", "
                + address.getProvince() + ", "
                + address.getCountry() + ", "
                + address.getZip();
    }
}
