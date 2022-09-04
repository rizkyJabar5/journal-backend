package com.journal.florist.backend.feature.customer.dto;

import com.journal.florist.app.common.utils.DateConverter;
import com.journal.florist.backend.feature.customer.model.Customers;
import com.journal.florist.backend.feature.ledger.model.Sales;
import com.journal.florist.backend.feature.utils.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
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
    private List<String> historyOrderId;
    private String addedBy;
    private String updatedBy;
    private String addedOn;
    private String updatedOn;

    public CustomerMapper mapToEntity(Customers mapper) {
        List<String> history = null;
        if(mapper.getHistoryOrder() != null){
            history = mapper.getHistoryOrder()
                    .stream()
                    .map(Sales::getSalesId).toList();
        }

        String modifiedBy = mapper.getLastModifiedBy();
        if(modifiedBy == null) {
            updatedBy = null;
        }

        LocalDateTime dateTime = DateConverter.toLocalDateTime(mapper.getCreatedAt());
        String addedDate = DateConverter.formatDateTime().format(dateTime);
        String updatedDate = null;
        if(mapper.getLastModifiedDate() != null) {
            LocalDateTime toLocalDate = DateConverter.toLocalDateTime(mapper.getLastModifiedDate());
            updatedDate = DateConverter.formatDateTime().format(toLocalDate);
        }

        return CustomerMapper.builder()
                .customerId(mapper.getPublicKey())
                .customerName(mapper.getName())
                .phoneNumber(mapper.getPhoneNumber())
                .companyName(mapper.getCompany().getCompanyName())
                .address(mapper.getCompany().getAddress())
                .historyOrderId(history)
                .addedBy(mapper.getCreatedBy())
                .updatedBy(modifiedBy)
                .addedOn(addedDate)
                .updatedOn(updatedDate)
                .build();
    }

}
