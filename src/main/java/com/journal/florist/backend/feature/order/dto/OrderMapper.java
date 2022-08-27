package com.journal.florist.backend.feature.order.dto;

import com.journal.florist.app.common.utils.DateConverter;
import com.journal.florist.backend.feature.order.model.Orders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class OrderMapper implements Serializable {

    private String orderId;
    private String customerName;
    private String phoneNumber;
    private List<DetailProduct> detailOfOrderProducts;
    private BigDecimal totalOrderAmount;
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

        List<DetailProduct> detailProducts = orders.getOrderDetails().parallelStream()
                .map(detail -> new DetailProduct(
                        detail.getProduct().getPublicKey(),
                        detail.getProduct().getProductName(),
                        detail.getQuantity(),
                        detail.getNotes(),
                        new BigDecimal(detail.getTotalPrice())))
                .toList();

        LocalDateTime dateTime = DateConverter.toLocalDateTime(orders.getCreatedAt());
        String addedDate = DateConverter.formatDateTime().format(dateTime);
        String modifiedDate = null;

        String modifiedBy = orders.getLastModifiedBy();

        if (orders.getLastModifiedDate() != null) {
            LocalDateTime toLocalDate = DateConverter.toLocalDateTime(orders.getLastModifiedDate());
            modifiedDate = DateConverter.formatDateTime().format(toLocalDate);
        }

        if (modifiedBy == null) {
            updatedBy = null;
        }

        String dateFormat = null;
        String timeFormat = null;
        if (orders.getOrderShipment().getDeliveryDate() != null) {
            var date = DateConverter.toLocalDate(orders.getOrderShipment().getDeliveryDate());
            dateFormat = DateConverter.formatDate().format(date);
            timeFormat = DateConverter.formatTime().format(date);
        }

        String fullAddress = null;
        if (orders.getOrderShipment().getDeliveryAddress() != null) {
            fullAddress = orders.getOrderShipment().getDeliveryAddress().getFullAddress();
        }
        return OrderMapper.builder()
                .orderId(orders.getPublicKey())
                .customerName(orders.getCustomer().getName())
                .phoneNumber(orders.getCustomer().getPhoneNumber())
                .detailOfOrderProducts(detailProducts)
                .totalOrderAmount(orders.getTotalOrderAmount())
                .paymentStatus(orders.getPaymentStatus().name())
                .orderStatus(orders.getOrderStatus().name())
                .recipientName(orders.getOrderShipment().getRecipientName())
                .deliveryAddress(fullAddress)
                .deliveryDate(dateFormat)
                .deliveryTime(timeFormat)
                .addedBy(orders.getCreatedBy())
                .createdAt(addedDate)
                .updatedBy(modifiedBy)
                .updatedAt(modifiedDate)
                .build();
    }

    @Data
    @AllArgsConstructor
    public static class DetailProduct {
        private String productId;
        private String productName;
        private Integer quantity;
        private String notes;
        private BigDecimal totalPricePerProduct;
    }
}
