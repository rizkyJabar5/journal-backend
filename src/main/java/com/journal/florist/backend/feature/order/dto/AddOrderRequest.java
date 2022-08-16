package com.journal.florist.backend.feature.order.dto;

import com.journal.florist.backend.feature.order.enums.OrderStatus;
import com.journal.florist.backend.feature.order.enums.PaymentStatus;
import com.journal.florist.backend.feature.utils.Address;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

@Data
public class AddOrderRequest implements Serializable {

    @NotBlank(message = "Customer id is required")
    private String customerId;
    @Valid
    private Set<OrderDetailDto> detailProduct;

    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;

    @NotBlank(message = "Recipient's name is is required")
    private String recipientName;

    @Valid
    private Address address;
    private String dateDelivery;
    private String timeDelivery;

    @Data
    public static class OrderDetailDto implements Serializable {
        @NotBlank(message = "Product id is required")
        private String productId;
        @Min(value = 1L, message = "Minimal quantity should be at 1")
        private Integer quantity;
        private String notes;
    }
}
