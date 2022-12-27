package com.journal.florist.backend.feature.customer.dto;

import com.journal.florist.app.common.utils.converter.DateConverter;
import com.journal.florist.backend.feature.customer.model.Customers;
import com.journal.florist.backend.feature.order.model.Orders;
import com.journal.florist.backend.feature.utils.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Component
public class CustomerMapper implements Serializable {

    private String customerId;
    private String customerName;
    private String phoneNumber;
    private String companyName;
    private Address address;
    private BigDecimal customerDebt;
    private List<String> historyOrderId;
    private String addedBy;
    private String updatedBy;
    private String addedOn;
    private String updatedOn;

    public CustomerMapper mapToEntity(Customers customer) {
        List<String> history = null;
        if(customer.getOrders() != null){
            history = customer.getOrders()
                    .stream()
                    .map(Orders::getPublicKey).toList();
        }

        BigDecimal totalDebt = customer.getCustomerDebt().getTotalDebt();
        if(totalDebt == null) {
            totalDebt = BigDecimal.ZERO;
        }

        String modifiedBy = customer.getLastModifiedBy();
        if(modifiedBy == null) {
            updatedBy = null;
        }

        LocalDateTime dateTime = DateConverter.toLocalDateTime(customer.getCreatedAt());
        String addedDate = DateConverter.formatDateTime().format(dateTime);
        String updatedDate = null;
        if(customer.getLastModifiedDate() != null) {
            LocalDateTime toLocalDate = DateConverter.toLocalDateTime(customer.getLastModifiedDate());
            updatedDate = DateConverter.formatDateTime().format(toLocalDate);
        }

        var customerCompany = customer.getCompany();

        String companyName = "";
        Address address = null;

        if(customerCompany != null) {
            companyName = customerCompany.getCompanyName();
            address = customerCompany.getAddress();
        }


        return CustomerMapper.builder()
                .customerId(customer.getPublicKey())
                .customerName(customer.getName())
                .phoneNumber(customer.getPhoneNumber())
                .companyName(companyName)
                .address(address)
                .customerDebt(totalDebt)
                .historyOrderId(history)
                .addedBy(customer.getCreatedBy())
                .updatedBy(modifiedBy)
                .addedOn(addedDate)
                .updatedOn(updatedDate)
                .build();
    }

}
